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

/**
 * Makes it possible to use different languages.
 * Localized strings for different languages are stored in a ResourceBundle in the projects resources.
 */
public class Localisation {

    private static ResourceBundle resourceBundle;
    private static final Map<String,String> nameMap = new HashMap<>();

    static {
        mapLanguages();
    }

    /**
     * Reloads, so that a different locale is used
     * @param localeId the new locale
     */
    public static void reload(String localeId) {
        String[] localeParts = localeId.split("_");
        Locale locale = new Locale(localeParts[0], localeParts[1]);
        resourceBundle = ResourceBundle.getBundle("lang.bundle", locale);
    }

    /**
     * Provides all locale Names
     * @return an array of the names of all available locales
     */
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

    /**
     * Finds the id of a locale based on its name (Name is specified in the ResourceBundle)
     * @param name the locale name
     * @return the locale id
     */
    public static String getId(String name) {
        return nameMap.keySet().stream().filter(id -> nameMap.get(id).equals(name)).findFirst().orElse("");
    }

    /**
     * Provides the name of a locale.
     * @param id the locale id
     * @return the locale name for the given locale id (the locale name is specified in the ResourceBundle)
     */
    public static String getName(String id) {
        return nameMap.get(id);
    }

    /**
     * Gets localized text.
     * @param key the key used to refer to localized text
     * @return the localized text using the current locale
     */
    public static String get(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            Logger.warn(e);
            return key;
        }
    }

    /**
     * Gets localized text and replaces all '~' with the input
     * @param key the key used to refer to localized text
     * @param input replaces all '~' in the localized text
     * @return the localized text
     */
    public static String get(String key, String input) {
        return get(key).replaceAll("~", input);
    }

}
