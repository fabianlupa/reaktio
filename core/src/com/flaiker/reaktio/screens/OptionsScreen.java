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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.flaiker.reaktio.Reaktio;
import com.flaiker.reaktio.game.Game;
import com.flaiker.reaktio.game.GameSettings;
import com.flaiker.reaktio.helper.DefaultActorListener;

public class OptionsScreen extends AbstractScreen {
    public static final String LOG = OptionsScreen.class.getSimpleName();

    private Game demoGame;

    public OptionsScreen(Reaktio reaktio, Game demoGame, Skin skin) {
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
        table.add("Options").align(1).spaceBottom(20);
        table.row();

        table.add().expandY().fill().row();

        // FPS-Counter-Checkbox
        final CheckBox fpsCounterCheckbox = new CheckBox("FPS-Counter", skin);
        fpsCounterCheckbox.setChecked(reaktio.getPreferencesManager().isFpsCounterEnabled());
        fpsCounterCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean enabled = fpsCounterCheckbox.isChecked();
                reaktio.getPreferencesManager().setFPSCounterEnabled(enabled);
            }
        });
        table.add(fpsCounterCheckbox).fillX().padBottom(30);
        table.row();

        table.add().expandY().fill().row();

        // Button "Back"
        TextButton backButton = new TextButton("BACK", skin);
        backButton.setColor(1, 1, 1, 0.9f);
        backButton.addListener(new DefaultActorListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                reaktio.setScreen(new MenuScreen(reaktio, demoGame, skin));
            }
        });
        table.add(backButton).padBottom(30).fillX();
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
