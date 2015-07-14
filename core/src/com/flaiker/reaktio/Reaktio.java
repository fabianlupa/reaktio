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

package com.flaiker.reaktio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.flaiker.reaktio.screens.MenuScreen;

public class Reaktio extends Game {
    private static final String LOG = Reaktio.class.getSimpleName();

    @Override
    public void create() {
        Gdx.app.log(LOG, "Creating game on " + Gdx.app.getType());
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.log(LOG, "Disposing game");
    }

    @Override
    public void pause() {
        super.pause();
        Gdx.app.log(LOG, "Pausing game");
    }

    @Override
    public void resume() {
        super.resume();
        Gdx.app.log(LOG, "Resuming game");
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Gdx.app.log(LOG, "Resizing game to: " + width + " x " + height);

        if (getScreen() == null) setScreen(new MenuScreen(this, null, null));
    }
}
