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

package com.flaiker.reaktio.servcies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.flaiker.reaktio.helper.ScoreArray;
import com.flaiker.reaktio.helper.ScoreEntry;

import java.util.ArrayList;
import java.util.Arrays;

public class PreferencesManager {
    public static final String LOG = PreferencesManager.class.getSimpleName();

    private static final String PREFS_NAME              = "reaktioprefs";
    private static final String PREF_FPSCOUNTER_ENABLED = "fpscounter.enabled";
    private static final String PREF_SCORE              = "score";

    private Preferences preferences;

    private Preferences getPreferences() {
        if (preferences == null) {
            preferences = Gdx.app.getPreferences(PREFS_NAME);
        }
        return preferences;
    }

    public boolean isFpsCounterEnabled() {
        return getPreferences().getBoolean(PREF_FPSCOUNTER_ENABLED, false);
    }

    public void setFPSCounterEnabled(boolean fpsCounterEnabled) {
        getPreferences().putBoolean(PREF_FPSCOUNTER_ENABLED, fpsCounterEnabled);
        getPreferences().flush();
        Gdx.app.log(LOG, "Set " + PREF_FPSCOUNTER_ENABLED + " to " + fpsCounterEnabled);
    }

    public ScoreEntry[] getScoreArray() {
        String string = getPreferences().getString(PREF_SCORE, null);
        if (string == null) return null;

        Json json = new Json();
        return json.fromJson(ScoreArray.class, string).scores;
    }

    public void addScoreEntry(ScoreEntry score) {
        Json json = new Json();

        String string = getPreferences().getString(PREF_SCORE, null);
        ScoreArray scores;

        if (string == null) {
            scores = new ScoreArray();
            scores.scores = new ScoreEntry[0];
        }
        else scores = json.fromJson(ScoreArray.class, string);

        ArrayList<ScoreEntry> scoreList = new ArrayList<ScoreEntry>(Arrays.asList(scores.scores));
        scoreList.add(score);

        scores.scores = scoreList.toArray(new ScoreEntry[scoreList.size()]);

        getPreferences().putString(PREF_SCORE, json.toJson(scores));
        getPreferences().flush();
        Gdx.app.log(LOG, "Added scoreentry");
    }
}
