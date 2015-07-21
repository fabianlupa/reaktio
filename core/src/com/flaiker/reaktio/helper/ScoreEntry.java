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

package com.flaiker.reaktio.helper;

import java.util.Arrays;
import java.util.Comparator;

public class ScoreEntry implements Comparable<ScoreEntry> {
    private float singleTouchScore;
    private float doubleTouchScore;
    private float dragSquareScore;
    private float overallScore;

    public ScoreEntry() {
        // Needed for json deserialization
    }

    public ScoreEntry(float singleTouchScore, float doubleTouchScore, float dragSquareScore, float overallScore) {
        this.singleTouchScore = singleTouchScore;
        this.doubleTouchScore = doubleTouchScore;
        this.dragSquareScore = dragSquareScore;
        this.overallScore = overallScore;
    }

    public String getComparedScore(ScoreEntry[] referenceScores) {
        if (referenceScores == null || referenceScores.length == 0) return toString();

        Arrays.sort(referenceScores, new SingleTouchComparator());
        float bestSingleTouchScore = referenceScores[0].getSingleTouchScore();
        float bestSingleTouchScoreDiff = singleTouchScore - bestSingleTouchScore;

        Arrays.sort(referenceScores, new DoubleTouchComparator());
        float bestDoubleTouchScore = referenceScores[0].getDoubleTouchScore();
        float bestDoubleTouchScoreDiff = doubleTouchScore - bestDoubleTouchScore;

        Arrays.sort(referenceScores, new DragSquareComparator());
        float bestDragSquareScore = referenceScores[0].getDragSquareScore();
        float bestDragSquareScoreDiff = dragSquareScore - bestDragSquareScore;

        Arrays.sort(referenceScores);
        float bestOverallScore = referenceScores[0].getOverallScore();
        float bestOverallScoreDiff = overallScore - bestOverallScore;

        return "SingleTouch: " + Tools.formatNumber(singleTouchScore, 1, 2) + " (" + Tools.formatNumber(bestSingleTouchScore, 1, 2) + ") " +
               Tools.formatNumber(bestSingleTouchScoreDiff, 1, 2, true) + "\n" +
               "DoubleTouch: " + Tools.formatNumber(doubleTouchScore, 1, 2) + " (" + Tools.formatNumber(bestDoubleTouchScore, 1, 2) + ") " +
               Tools.formatNumber(bestDoubleTouchScoreDiff, 1, 2, true) + "\n" +
               "Drag:        " + Tools.formatNumber(dragSquareScore, 1, 2) + " (" + Tools.formatNumber(bestDragSquareScore, 1, 2) + ") " +
               Tools.formatNumber(bestDragSquareScoreDiff, 1, 2, true) + "\n\n" +
               "Overall:     " + Tools.formatNumber(overallScore, 1, 2) + " (" + Tools.formatNumber(bestOverallScore, 1, 2) + ") " +
               Tools.formatNumber(bestOverallScoreDiff, 1, 2, true);
    }

    @Override
    public String toString() {
        return "SingleTouch: " + Tools.formatNumber(singleTouchScore, 1, 2) + "\n" +
               "DoubleTouch: " + Tools.formatNumber(doubleTouchScore, 1, 2) + "\n" +
               "Drag:        " + Tools.formatNumber(dragSquareScore, 1, 2) + "\n\n" +
               "Overall:     " + Tools.formatNumber(overallScore, 1, 2) + "\n";
    }

    public float getSingleTouchScore() {
        return singleTouchScore;
    }

    public void setSingleTouchScore(float singleTouchScore) {
        this.singleTouchScore = singleTouchScore;
    }

    public float getDoubleTouchScore() {
        return doubleTouchScore;
    }

    public void setDoubleTouchScore(float doubleTouchScore) {
        this.doubleTouchScore = doubleTouchScore;
    }

    public float getDragSquareScore() {
        return dragSquareScore;
    }

    public void setDragSquareScore(float dragSquareScore) {
        this.dragSquareScore = dragSquareScore;
    }

    public float getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(float overallScore) {
        this.overallScore = overallScore;
    }

    @Override
    public int compareTo(ScoreEntry o) {
        return Float.compare(overallScore, o.getOverallScore());
    }

    public class SingleTouchComparator implements Comparator<ScoreEntry> {
        @Override
        public int compare(ScoreEntry o1, ScoreEntry o2) {
            return Float.compare(o1.getSingleTouchScore(), o2.getSingleTouchScore());
        }
    }

    public class DoubleTouchComparator implements Comparator<ScoreEntry> {
        @Override
        public int compare(ScoreEntry o1, ScoreEntry o2) {
            return Float.compare(o1.getDoubleTouchScore(), o2.getDoubleTouchScore());
        }
    }

    public class DragSquareComparator implements Comparator<ScoreEntry> {
        @Override
        public int compare(ScoreEntry o1, ScoreEntry o2) {
            return Float.compare(o1.getDragSquareScore(), o2.getDragSquareScore());
        }
    }
}
