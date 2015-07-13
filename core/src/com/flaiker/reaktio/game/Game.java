/***********************************************************************************
 * The MIT License (MIT)                                                           *
 *                                                                                 *
 * Copyright (c) 2015 Fabian Lupa                                                  *
 *                                                                                 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy    *
 * of this software and associated documentation files (the "Software"), to deal   *
 * in the Software without restriction, including without limitation the rights    *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell       *
 * copies of the Software, and to permit persons to whom the Software is           *
 * furnished to do so, subject to the following conditions:                        *
 *                                                                                 *
 * The above copyright notice and this permission notice shall be included in all  *
 * copies or substantial portions of the Software.                                 *
 *                                                                                 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR      *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,        *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE     *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER          *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,   *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE   *
 * SOFTWARE.                                                                       *
 ***********************************************************************************/

package com.flaiker.reaktio.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.flaiker.reaktio.entities.AbstractEntity;
import com.flaiker.reaktio.entities.DoubleTouchCircleEntity;
import com.flaiker.reaktio.entities.DragSquareEntity;
import com.flaiker.reaktio.entities.SingleTouchCircleEntity;
import com.flaiker.reaktio.helper.Tools;

public class Game implements InputProcessor {
    private GameSettings gameSettings;

    private float                 gameTimeElapsed    = 0;
    private String                score              = null;
    private float                 timeSinceLastSpawn = 0;
    private GameState             gameState          = GameState.START;
    private Array<AbstractEntity> entities           = new Array<AbstractEntity>();

    private final float screenWidth, screenHeight;
    private final OrthographicCamera camera;

    public Game(GameSettings gameSettings, float screenWidth, float screenHeight, OrthographicCamera camera) {
        this.gameSettings = gameSettings;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.camera = camera;

        if (gameSettings.gameMode == GameMode.DEMO) gameState = GameState.RUNNING;
    }

    public void render(SpriteBatch batch) {
        for (AbstractEntity entity : entities) {
            if (entity != null) entity.draw(batch);
        }
        if (gameState == GameState.RUNNING) {
            updateGame();
        }
    }

    private void updateGame() {
        if (gameSettings.gameMode == GameMode.NORMAL_TIME_LIMIT || gameSettings.gameMode == GameMode.NORMAL_CONTINUOUS) {
            gameTimeElapsed += Gdx.graphics.getDeltaTime();

            if (gameSettings.gameMode == GameMode.NORMAL_TIME_LIMIT) {
                if (getGameTimeRemaining() <= 0) {
                    resetEntityVisibility();
                    gameState = GameState.END;
                    return;
                }
            }
        }
        timeSinceLastSpawn += Gdx.graphics.getDeltaTime();
        if (timeSinceLastSpawn >= Math.random() * gameSettings.spawnDelayRandomPart + gameSettings.minSpawnDelay) spawnEntity();
    }

    private void spawnEntity() {
        Entity toSpawnEntity = Tools.randomEnum(Entity.class);
        AbstractEntity entity = null;
        boolean selfSolving = gameSettings.gameMode == GameMode.DEMO;
        switch (toSpawnEntity) {
            case DRAG_SQUARE_ENTITY:
                entity = new DragSquareEntity(
                        (float) (Math.random() * (screenWidth - DragSquareEntity.WIDTH - DragSquareEntity.TRAVEL_DISTANCE * 2) +
                                 DragSquareEntity.TRAVEL_DISTANCE),
                        (float) (Math.random() * (screenHeight - DragSquareEntity.HEIGHT - DragSquareEntity.TRAVEL_DISTANCE * 2) +
                                 DragSquareEntity.TRAVEL_DISTANCE), Tools.randomEnum(DragSquareEntity.Direction.class), selfSolving);
                break;
            case SINGLE_TOUCH_ENTITY:
                entity = new SingleTouchCircleEntity((float) (Math.random() * (screenWidth - SingleTouchCircleEntity.WIDTH)),
                                                     (float) (Math.random() * (screenHeight - SingleTouchCircleEntity.HEIGHT)),
                                                     selfSolving);
                break;
            case DOUBLE_TOUCH_ENTITY:
                entity = new DoubleTouchCircleEntity((float) (Math.random() * (screenWidth - SingleTouchCircleEntity.WIDTH)),
                                                     (float) (Math.random() * (screenHeight - SingleTouchCircleEntity.HEIGHT)),
                                                     selfSolving);
                break;
        }
        entities.add(entity);
        timeSinceLastSpawn = 0;
    }

    private Vector3 screenCoordsToRealCoords(int screenX, int screenY, int screenZ) {
        Vector3 worldCoordinates = new Vector3(screenX, screenY, screenZ);
        camera.unproject(worldCoordinates);
        return worldCoordinates;
    }

    private AbstractEntity findHitEntity(int screenX, int screenY) {
        Vector3 worldCoordinates = screenCoordsToRealCoords(screenX, screenY, 0);

        AbstractEntity entity;
        for (int i = entities.size - 1; i >= 0; i--) {
            entity = entities.get(i);
            if (entity != null && !entity.isSolved() && entity.getX() <= worldCoordinates.x &&
                (entity.getX() + entity.getWidth() >= worldCoordinates.x) &&
                entity.getY() <= worldCoordinates.y &&
                (entity.getY() + entity.getHeight() >= worldCoordinates.y)) {
                return entity;
            }
        }
        return null;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private void resetEntityVisibility() {
        for (AbstractEntity entity : entities) {
            entity.resetVisibility();
        }
    }

    public GameMode getGameMode() {
        return gameSettings.gameMode;
    }

    public String getScore() {
        if (score == null) {
            float singleTouchTime = 0;
            int singleTouchCount = 0;
            float doubleTouchTime = 0;
            int doubleTouchCount = 0;
            float dragTime = 0;
            int dragCount = 0;
            for (AbstractEntity entity : entities) {
                if (entity instanceof SingleTouchCircleEntity) {
                    singleTouchTime += entity.getTimeAlive();
                    singleTouchCount++;
                } else if (entity instanceof DoubleTouchCircleEntity) {
                    doubleTouchTime += entity.getTimeAlive();
                    doubleTouchCount++;
                } else if (entity instanceof DragSquareEntity) {
                    dragTime += entity.getTimeAlive();
                    dragCount++;
                }
            }
            score = "SingleTouch: " + Tools.formatNumber(singleTouchTime / singleTouchCount, 1, 2) + "\n" +
                    "DoubleTouch: " + Tools.formatNumber(doubleTouchTime / doubleTouchCount, 1, 2) + "\n" +
                    "Drag:        " + Tools.formatNumber(dragTime / dragCount, 1, 2) + "\n\n" +
                    "Overall:     " +
                    Tools.formatNumber((singleTouchTime + doubleTouchTime + dragTime) / (singleTouchCount + doubleTouchCount + dragCount),
                                       1, 2);
        }

        return score;
    }

    public float getGameTimeElapsed() {
        return gameTimeElapsed;
    }

    public float getGameTimeRemaining() {
        return gameSettings.maxGameTime - gameTimeElapsed;
    }

    private enum Entity {
        DRAG_SQUARE_ENTITY, SINGLE_TOUCH_ENTITY, DOUBLE_TOUCH_ENTITY
    }

    public enum GameState {
        START, RUNNING, END
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gameSettings.gameMode == GameMode.NORMAL_TIME_LIMIT || gameSettings.gameMode == GameMode.NORMAL_CONTINUOUS) {
            if (gameState == GameState.START) {
                gameState = GameState.RUNNING;
                return true;
            } else if (gameState == GameState.RUNNING) {
                AbstractEntity hitEntity = findHitEntity(screenX, screenY);
                if (hitEntity != null) {
                    Vector3 worldCoordinates = screenCoordsToRealCoords(screenX, screenY, 0);
                    if (hitEntity.canTouchDown()) return hitEntity.touchDown(worldCoordinates.x, worldCoordinates.y);
                }
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (gameSettings.gameMode == GameMode.NORMAL_TIME_LIMIT || gameSettings.gameMode == GameMode.NORMAL_CONTINUOUS) {
            if (gameState == GameState.RUNNING) {
                AbstractEntity hitEntity = findHitEntity(screenX, screenY);
                if (hitEntity != null) {
                    Vector3 worldCoordinates = screenCoordsToRealCoords(screenX, screenY, 0);
                    if (hitEntity.canTouchDrag()) return hitEntity.touchDragged(worldCoordinates.x, worldCoordinates.y);
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
