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

public class GameSettings {
    public final GameMode gameMode;
    public final float    maxGameTime;
    public final float    minSpawnDelay;
    public final float    spawnDelayRandomPart;

    public GameSettings(GameMode gameMode, float maxGameTime, float minSpawnDelay, float spawnDelayRandomPart) {
        this.gameMode = gameMode;
        this.maxGameTime = maxGameTime;
        this.minSpawnDelay = minSpawnDelay;
        this.spawnDelayRandomPart = spawnDelayRandomPart;
    }

    public static GameSettings newContinuousGameSettings(float minSpawnDelay, float spawnDelayRandomPart) {
        return new GameSettings(GameMode.NORMAL_CONTINUOUS, 0, minSpawnDelay, spawnDelayRandomPart);
    }

    public static GameSettings newTimeLimitGameSettings(float maxGameTime, float minSpawnDelay, float spawnDelayRandomPart) {
        return new GameSettings(GameMode.NORMAL_TIME_LIMIT, maxGameTime, minSpawnDelay, spawnDelayRandomPart);
    }

    public static GameSettings newDemoGameSettings() {
        return new GameSettings(GameMode.DEMO, 0, 1, 10);
    }
}
