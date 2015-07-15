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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AbstractEntity {
    protected Sprite sprite;

    private boolean selfSolving;

    private boolean solved    = false;
    private float   timeAlive = 0;


    public AbstractEntity(TextureRegion textureRegion, float x, float y, float width, float height, boolean selfSolving) {
        sprite = new Sprite(textureRegion);
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        this.selfSolving = selfSolving;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public float getX() {return sprite.getX();}

    public float getY() {return sprite.getY();}

    public float getWidth() {return sprite.getWidth();}

    public float getHeight() {return sprite.getHeight();}

    public float getTimeAlive() {
        return timeAlive;
    }

    public void draw(SpriteBatch batch) {
        if (!isSolved()) {
            timeAlive += Gdx.graphics.getDeltaTime();
        }
        if (selfSolving && !isSolved()) {
            selfSolve();
        }

        sprite.draw(batch);
    }

    public void resetVisibility() {
        setSolved(true);
        sprite.setAlpha(1);
    }

    protected void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    public abstract void selfSolve();

    public abstract boolean canTouchDown();

    public abstract boolean canTouchDrag();

    public boolean touchDown(float x, float y) {return false;}

    public boolean touchDragged(float x, float y) {return false;}
}
