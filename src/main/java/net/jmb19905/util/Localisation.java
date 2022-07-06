/*
 * A simple Messenger written in Java
 * Copyright (C) 2020-2022  Jared M. Bennett
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

import java.util.*;

public class Localisation {

    private static ResourceBundle resourceBundle;
    private static final Map<String,String> nameMap = new HashMap<>();

    static {
        mapLanguages();
    }

    public static void reload(String localeId) {
        String[] localeParts = localeId.split("_");
        Locale locale = new Locale(localeParts[0], localeParts[1]);
        resourceBundle = ResourceBundle.getBundle("lang.bundle", locale);
    }

    public static String[] getLocaleNames() {
        return nameMap.values().toArray(new String[0]);
    }

    private static void mapLanguages() {
        nameMap.clear();
        String[] resourcePaths = ResourceUtility.getResourceFiles(Localisation.class, "lang");
        System.out.println("Lang resource paths: " + Arrays.toString(resourcePaths));
        Arrays.stream(resourcePaths)
                .filter(fileName -> !fileName.isEmpty() && !fileName.isBlank())
                .map(fileName -> {
                    String[] parts = fileName.split("_");
                    return parts[1] + "_" + (parts[2].replace(".properties", ""));
                }).forEach(localeId -> {
                    String[] localeParts = localeId.split("_");
                    Locale locale = new Locale(localeParts[0], localeParts[1]);
                    resourceBundle = ResourceBundle.getBundle("lang.bundle", locale);
                    nameMap.put(localeId, get("name"));
                });
    }

    public static String getId(String name) {
        return nameMap.keySet().stream().filter(id -> nameMap.get(id).equals(name)).findFirst().orElse("");
    }

    public static String getName(String id) {
        return nameMap.get(id);
    }

    public static String get(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            Logger.warn(e);
            return key;
        }
    }

    public static String get(String key, String input) {
        return get(key).replaceAll("~", input);
    }

}
