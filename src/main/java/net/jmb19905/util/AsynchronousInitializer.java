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

import java.util.concurrent.CountDownLatch;

/**
 * Initializes a value asynchronously
 * @param <T> the type of the value to be initialized
 */
public class AsynchronousInitializer<T> {

    private final CountDownLatch cancelLatch = new CountDownLatch(1);
    private T t = null;

    /**
     * Creates an object of type AsynchronousInitializer
     */
    public AsynchronousInitializer() {}

    /**
     * Initialized the value
     * @param initT the value
     */
    public void init(T initT) {
        t = initT;
        cancel();
    }

    /**
     * Releases the waiting threads
     */
    public void cancel() {
        cancelLatch.countDown();
    }

    /**
     * Waits until the value is initialized and then returns it
     * @return the initialized value
     */
    public T get() {
        try {
            cancelLatch.await();
        } catch (InterruptedException e) {
            Logger.error(e);
        }
        return t;
    }

}
