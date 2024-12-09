/*
    A simple Messenger written in Java
    Copyright (C) 2020-2022  Jared M. Bennett

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package net.jmb19905.util;

/**
 * Color values for ANSI compatible consoles
 */
public final class ANSIColors {

    private ANSIColors(){}
    
    private static final String ANSI_ESCAPE = "\u001B[";

    /**
     *
     * Reset Code: Resets the style to default
     */
    private static final String ANSI_RESET = "0m";

    /**
     *
     * Black Code: Makes subsequent text black
     */
    private static final String ANSI_BLACK = "30m";

    /**
     *
     * Red Code: Makes subsequent text red
     */
    private static final String ANSI_RED = "31m";

    /**
     *
     * Green Code: Makes subsequent text green
     */
    private static final String ANSI_GREEN = "32m";

    /**
     *
     * Yellow Code: Makes subsequent text yellow
     */
    private static final String ANSI_YELLOW = "33m";

    /**
     *
     * Blue Code: Makes subsequent text blue
     */
    private static final String ANSI_BLUE = "34m";

    /**
     *
     * Purple Code: Makes subsequent text purple
     */
    private static final String ANSI_PURPLE = "35m";

    /**
     *
     * Cyan Code: Makes subsequent text cyan
     */
    private static final String ANSI_CYAN = "36m";

    /**
     *
     * White Code: Makes subsequent text white
     */
    private static final String ANSI_WHITE = "37;1m";

    /**
     * black color code
     * @return the black code
     */
    public static String getBlack() {
        return ANSI_ESCAPE + ANSI_BLACK;
    }

    /**
     * blue color code
     * @return the blue code
     */
    public static String getBlue() {
        return ANSI_ESCAPE + ANSI_BLUE;
    }

    /**
     * green color code
     * @return the green code
     */
    public static String getGreen() {
        return ANSI_ESCAPE + ANSI_GREEN;
    }

    /**
     * cyan color code
     * @return the cyan code
     */
    public static String getCyan() {
        return ANSI_ESCAPE + ANSI_CYAN;
    }

    /**
     * purple color code
     * @return the purple code
     */
    public static String getPurple() {
        return ANSI_ESCAPE + ANSI_PURPLE;
    }

    /**
     * red color code
     * @return the red code
     */
    public static String getRed() {
        return ANSI_ESCAPE + ANSI_RED;
    }

    /**
     * reset code
     * @return the reset code
     */
    public static String getReset() {
        return ANSI_ESCAPE + ANSI_RESET;
    }

    /**
     * white color code
     * @return the white code
     */
    public static String getWhite() {
        return ANSI_ESCAPE + ANSI_WHITE;
    }

    /**
     * yellow color code
     * @return the yellow code
     */
    public static String getYellow() {
        return ANSI_ESCAPE + ANSI_YELLOW;
    }

    /**
     * Removes the color codes from strings - useful for incompatible consoles or files
     * @param in the string
     * @return the string with stripped out color codes
     */
    public static String replaceANSI(String in) {
        return in.replace(getBlack(), "").replace(getBlue(), "").replace(getRed(), "")
                .replace(getReset(), "").replace(getCyan(), "").replace(getGreen(), "")
                .replace(getPurple(), "").replace(getWhite(), "").replace(getYellow(), "");

    }

}
