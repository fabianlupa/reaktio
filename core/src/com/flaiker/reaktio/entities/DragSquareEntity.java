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

package com.flaiker.reaktio.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class DragSquareEntity extends AbstractEntity {
    private static final String LOG = DragSquareEntity.class.getSimpleName();

    public static final float TRAVEL_DISTANCE = 100;
    public static final float WIDTH           = 100;
    public static final float HEIGHT          = 100;

    private final Vector2   startPos;
    private final Direction direction;

    private float xDif = 0;
    private float yDif = 0;

    //TODO: Add Touch-Scroll-Responsiveness
    public DragSquareEntity(float x, float y, Direction direction, boolean selfSolving) {
        super(new TextureRegion(new Texture(Gdx.files.internal("DragSquareEntity.png"))), x, y, WIDTH, HEIGHT, selfSolving);

        startPos = new Vector2(x, y);
        this.direction = direction;

        switch (direction) {
            case LEFT:
                sprite.rotate90(true);
                sprite.rotate90(true);
                break;
            case RIGHT:
                break;
            case UP:
                sprite.rotate90(false);
                break;
            case DOWN:
                sprite.rotate90(true);
                break;
        }
    }

    private float getTravelledDistance() {
        Vector2 tmpVec = new Vector2(startPos);
        return Math.abs(tmpVec.sub(getX(), getY()).len());
    }

    @Override
    public boolean canTouchDown() {
        return true;
    }

    @Override
    public boolean canTouchDrag() {
        return true;
    }

    @Override
    public boolean touchDown(float x, float y) {
        xDif = x - getX();
        yDif = y - getY();
        return true;
    }

    @Override
    public boolean touchDragged(float x, float y) {
        switch (direction) {
            case LEFT:
                if ((x - xDif) < getX()) setPosition(x - xDif, getY());
                break;
            case RIGHT:
                if ((x - xDif) > getX()) setPosition(x - xDif, getY());
                break;
            case UP:
                if ((y - yDif) > getY()) setPosition(getX(), y - yDif);
                break;
            case DOWN:
                if ((y - yDif) < getY()) setPosition(getX(), y - yDif);
                break;
        }
        if (getTravelledDistance() > TRAVEL_DISTANCE) setSolved(true);
        if (!isSolved()) sprite.setColor(1, 1, 1, 1 - getTravelledDistance() / TRAVEL_DISTANCE);
        else if (isSolved() && sprite.getColor().a != 0) sprite.setColor(1, 1, 1, 0);

        return true;
    }

    @Override
    public void selfSolve() {
        if (getTimeAlive() > 1) {
            switch (direction) {
                case LEFT:
                    touchDragged(getX() - 100 * Gdx.graphics.getDeltaTime(), 1);
                    break;
                case RIGHT:
                    touchDragged(getX() + 100 * Gdx.graphics.getDeltaTime(), 1);
                    break;
                case UP:
                    touchDragged(1, getY() + 100 * Gdx.graphics.getDeltaTime());
                    break;
                case DOWN:
                    touchDragged(1, getY() - 100 * Gdx.graphics.getDeltaTime());
                    break;
            }
        }
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
