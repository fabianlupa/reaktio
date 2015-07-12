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

public class MenuScreen extends AbstractScreen {
    public static final String LOG = MenuScreen.class.getSimpleName();

    private Table table;
    private Game  demoGame;

    public MenuScreen(Reaktio game) {
        super(game);
    }

    public MenuScreen(Reaktio game, Game demoGame) {
        super(game);
        this.demoGame = demoGame;
    }

    public MenuScreen(Reaktio game, Game demoGame, Skin skin) {
        super(game, skin);
        this.demoGame = demoGame;
    }

    public MenuScreen(Reaktio game, Skin skin) {
        super(game, skin);
    }

    @Override
    public void show() {
        super.show();

        if (demoGame == null) demoGame = new Game(GameSettings.newDemoGameSettings(), SCREEN_WIDTH, SCREEN_HEIGHT, camera);

        table = new Table(skin);
        table.setFillParent(true);
        uiStage.addActor(table);

        table.add().padBottom(50).row();
        Label titleLabel = new Label("Reaktio", skin, "digital7-92", Color.WHITE);
        //titleLabel.setFontScale(2);
        table.add(titleLabel).spaceBottom(5).align(1);
        table.row();
        table.add("A reaction test game").align(1).spaceBottom(20);
        table.row();

        // register the button "Start"
        TextButton startGameButton = new TextButton("START", skin);
        startGameButton.setColor(1, 1, 1, 0.9f);
        startGameButton.addListener(new DefaultActorListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.setScreen(new GameScreen(game, skin));
            }
        });
        table.add(startGameButton).expand().fill().pad(100, 100, 100, 100);
        table.row();

        // register the button "Highscore"
        TextButton highscoreButton = new TextButton("HIGHSCORE", skin);
        highscoreButton.setColor(1, 1, 1, 0.9f);
        highscoreButton.addListener(new DefaultActorListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                //game.setScreen(new HighscoreScreen(game, demoGame, skin));
            }
        });
        table.add(highscoreButton).expand().fill().pad(0, 100, 100, 100);
        table.row();

        // register the button "Options"
        TextButton optionsButton = new TextButton("OPTIONS", skin);
        optionsButton.setColor(1, 1, 1, 0.9f);
        optionsButton.addListener(new DefaultActorListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                //game.setScreen(new OptionsScreen(game));
            }
        });
        table.add(optionsButton).expand().fill().pad(0, 100, 100, 100).row();

        table.add(new Label("www.flaiker.com", skin));

        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    protected void preUIrender(float delta) {
        demoGame.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();

    }

    @Override
    protected String getName() {
        return LOG;
    }
}
