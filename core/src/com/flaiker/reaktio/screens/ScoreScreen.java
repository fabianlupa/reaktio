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

package com.flaiker.reaktio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.flaiker.reaktio.Reaktio;
import com.flaiker.reaktio.game.Game;
import com.flaiker.reaktio.helper.DefaultActorListener;

public class ScoreScreen extends AbstractScreen {
    public static final String LOG = ScoreScreen.class.getSimpleName();

    private Game game = null;

    public ScoreScreen(Reaktio reaktio, Game game, Skin skin) {
        super(reaktio, skin);
        this.game = game;
    }

    @Override
    public void show() {
        super.show();

        Gdx.app.log(LOG, game.getScore());

        Table table = new Table(skin);
        table.setFillParent(true);
        uiStage.addActor(table);

        table.add().padBottom(50).row();
        Label titleLabel = new Label("SCORE", skin, "digital7-92", Color.WHITE);
        table.add(titleLabel).spaceBottom(5).align(1);
        table.row();

        Container<Label> scoreListContainer = new Container<Label>(new Label(game.getScore(), skin));
        scoreListContainer.setBackground(skin.getDrawable("default-round"));
        scoreListContainer.setColor(1, 1, 1, 0.9f);

        table.add(scoreListContainer).expand().fill().pad(100, 100, 0, 100).row();

        // register the button "Try again"
        TextButton tryAgainButton = new TextButton("TRY AGAIN", skin);
        tryAgainButton.setColor(1, 1, 1, 0.9f);
        tryAgainButton.addListener(new DefaultActorListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                reaktio.setScreen(new GameScreen(reaktio, game.getGameSettings(), skin));
            }
        });
        table.add(tryAgainButton).expand().fill().pad(100, 100, 0, 100).row();

        // register the button "Back"
        TextButton backButton = new TextButton("BACK TO MAINMENU", skin);
        backButton.setColor(1, 1, 1, 0.9f);
        backButton.addListener(new DefaultActorListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                reaktio.setScreen(new MenuScreen(reaktio, null, skin));
            }
        });
        table.add(backButton).expand().fill().pad(100, 100, 50, 100).row();

        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    protected String getName() {
        return LOG;
    }

    @Override
    protected void preUIrender(float delta) {
        game.render(batch);
    }
}
