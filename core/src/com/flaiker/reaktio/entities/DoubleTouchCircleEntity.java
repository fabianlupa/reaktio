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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DoubleTouchCircleEntity extends AbstractEntity {
    public static final float WIDTH  = 100;
    public static final float HEIGHT = 100;

    private int     touchCount               = 0;
    private boolean fadeOutAnimationFinished = false;

    public DoubleTouchCircleEntity(float x, float y, boolean selfSolving) {
        super(new TextureRegion(new Texture(Gdx.files.internal("DoubleTouchCircleEntity.png"))), x, y, WIDTH, HEIGHT, selfSolving);
    }

    @Override
    public boolean canTouchDown() {
        return true;
    }

    @Override
    public boolean canTouchDrag() {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y) {
        if (!isSolved()) {
            touchCount++;
            if (touchCount == 1) sprite.setTexture(new Texture(Gdx.files.internal("SingleTouchCircleEntity.png")));
            if (touchCount == 2) setSolved(true);
            return true;
        }
        return false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        // Fade-Out-Animation
        if (isSolved() && !fadeOutAnimationFinished) {
            float newAlpha = sprite.getColor().a - (2.5f * Gdx.graphics.getDeltaTime());
            if (newAlpha > 0) sprite.setAlpha(newAlpha);
            else if (newAlpha < 0) {
                sprite.setAlpha(0);
                fadeOutAnimationFinished = true;
            }
        }
    }

    @Override
    public void resetVisibility() {
        super.resetVisibility();
        sprite.setTexture(new Texture(Gdx.files.internal("DoubleTouchCircleEntity.png")));
    }

    @Override
    public void selfSolve() {
        if (getTimeAlive() > 1 && touchCount == 0) touchDown(1, 1);
        if (getTimeAlive() > 1.3 && touchCount == 1) touchDown(1, 1);

    }
}
