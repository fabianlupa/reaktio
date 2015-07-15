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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.flaiker.reaktio.Reaktio;
import com.flaiker.reaktio.game.Game;
import com.flaiker.reaktio.game.GameSettings;
import com.flaiker.reaktio.helper.DefaultActorListener;

public class ModeSelectionScreen extends AbstractScreen {
    public static final String LOG = ModeSelectionScreen.class.getSimpleName();

    private Game demoGame;

    public ModeSelectionScreen(Reaktio reaktio, Game demoGame, Skin skin) {
        super(reaktio, skin);
        this.demoGame = demoGame;
    }

    @Override
    public void show() {
        super.show();

        if (demoGame == null) demoGame = new Game(GameSettings.newDemoGameSettings(), SCREEN_WIDTH, SCREEN_HEIGHT, camera);

        Table table = new Table(skin);
        table.setFillParent(true);
        uiStage.addActor(table);

        table.add().padBottom(50).row();
        Label titleLabel = new Label("Reaktio", skin, "digital7-92", Color.WHITE);
        //titleLabel.setFontScale(2);
        table.add(titleLabel).spaceBottom(5).align(1);
        table.row();
        table.add("Select a Gamemode").align(1).spaceBottom(20);
        table.row();

        // register the button "Timelimit"
        TextButton timelimitButton = new TextButton("Timelimit", skin);
        timelimitButton.setColor(1, 1, 1, 0.9f);
        timelimitButton.addListener(new DefaultActorListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                reaktio.setScreen(new GameScreen(reaktio, GameSettings.newTimeLimitGameSettings(30, 2, 2), skin));
            }
        });
        table.add(timelimitButton).expand().fill().pad(0, 100, 100, 100);
        table.row();

        // register the button "Continuous"
        TextButton continuousButton = new TextButton("Continuous", skin);
        continuousButton.setColor(1, 1, 1, 0.9f);
        continuousButton.addListener(new DefaultActorListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                reaktio.setScreen(new GameScreen(reaktio, GameSettings.newContinuousGameSettings(2, 2), skin));
            }
        });
        table.add(continuousButton).expand().fill().pad(100, 100, 100, 100);
        table.row();

        table.add(new Label("www.flaiker.com", skin));

        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    protected String getName() {
        return LOG;
    }

    @Override
    protected void preUIrender(float delta) {
        demoGame.render(batch);
    }
}
