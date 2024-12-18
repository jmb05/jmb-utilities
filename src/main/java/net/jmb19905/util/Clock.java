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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clock used to track time
 */
public class Clock {

    private static long start;

    /**
     * Creates an object of type Clock
     */
    public Clock() {}

    /**
     * Starts the clock
     */
    public static void init() {
        start = System.currentTimeMillis();
    }

    /**
     * Provides the time the clock was started as timestamp
     * @return the start time
     */
    public static long getStart() {
        return start;
    }

    /**
     * Provides the amount of time which has passed since the start (in milliseconds)
     * @return the current elapsed time
     */
    public static int getTime() {
        return (int) (System.currentTimeMillis() - start);
    }

    /**
     * Formats the current date
     * @param pattern the pattern for formatting
     * @return the formatted date
     */
    public static String getCompactDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date now = new Date();
        return format.format(now);
    }
}
