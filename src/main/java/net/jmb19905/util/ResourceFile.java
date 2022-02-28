/*
 * A simple Messenger written in Java
 * Copyright (C) 2020-2022 Jared M. Bennett
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.jmb19905.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceFile {

    private static final String FILE_SEPARATOR = "/";

    private final String path;
    private final String name;

    public ResourceFile(String path) {
        this.path = path;
        String[] dirs = path.split(FILE_SEPARATOR);
        this.name = dirs[dirs.length - 1];
    }

    public ResourceFile(String... paths) {
        StringBuilder builder = new StringBuilder();
        for (String part : paths) {
            builder.append(FILE_SEPARATOR).append(part);
        }
        this.path = builder.toString();
        String[] dirs = path.split(FILE_SEPARATOR);
        this.name = dirs[dirs.length - 1];
    }

    public ResourceFile(ResourceFile file, String subFile) {
        this.path = file.path + FILE_SEPARATOR + subFile;
        this.name = subFile;
    }

    public ResourceFile(ResourceFile file, String... subFiles) {
        StringBuilder builder = new StringBuilder();
        builder.append(file.path);
        for (String part : subFiles) {
            builder.append(FILE_SEPARATOR).append(part);
        }
        this.path = builder.toString();
        String[] dirs = path.split(FILE_SEPARATOR);
        this.name = dirs[dirs.length - 1];
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return getPath();
    }

    /**
     * @return the InputStream to access the Resource
     */
    public InputStream getStream() {
        return ResourceUtility.getResource(path);
    }

    /**
     * @return the BufferedReader to access the Resource
     */
    public BufferedReader getReader() {
        try {
            InputStreamReader isr = new InputStreamReader(getStream());
            return new BufferedReader(isr);
        } catch (Exception e) {
            System.err.println("Couldn't get reader for " + path);
            throw e;
        }
    }

    public String getName() {
        return name;
    }

}
