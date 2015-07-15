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

import java.util.Random;

public class Tools {
    private static Random random = new Random(System.currentTimeMillis());

    /**
     * Returns a random element of an enumeration
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * This function is needed because GWT has no "translation" for DecimalFormat
     * @param number The number to be formatted
     * @param minPreSeparatorPlacesCount The count wanted decimal places
     * @param decimalPlacesCount Count of decimal places
     * @return The formatted number
     */
    public static String formatNumber(double number, int minPreSeparatorPlacesCount, int decimalPlacesCount) {
        String[] split = (String.valueOf(number)).split("\\.");
        if (split.length != 2) return null;

        String a = split[0];
        String b = split[1];

        while (a.length() < minPreSeparatorPlacesCount) a = "0" + a;
        while (b.length() < decimalPlacesCount) b = b + "0";

        if (b.length() > decimalPlacesCount) b = b.substring(0, decimalPlacesCount);

        return a + '.' + b;
    }
}
