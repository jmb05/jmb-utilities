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

package net.jmb19905.util.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jmb19905.util.Logger;
import net.jmb19905.util.ShutdownManager;
import net.jmb19905.util.bootstrapping.DeployState;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Manages loading and saving of configs
 */
public class ConfigManager {

    private ConfigManager(){}

    private static String configPath;
    private static boolean initialized = false;

    /**
     * Initialized the config manager
     * @param name the name is needed for finding the correct config files
     * @param deployState the deploy-state is needed to choose the correct path for finding the config files
     */
    public static void init(String name, DeployState deployState) {
        if(deployState == DeployState.CLIENT) {
            if (System.getProperty("os.name").equals("Linux")) {
                configPath = System.getProperty("user.home") + "/.config/" + name +  "/";
                initialized = true;
                return;
            }
        }
        configPath = "config/";
        initialized = true;
    }

    /**
     * Changes to path where the configs are located to a user defined one
     * @param configPath the new path
     */
    @SuppressWarnings("unused")
    public static void setConfigPath(String configPath) {
        ConfigManager.configPath = configPath;
    }

    /**
     * Provides the current config file path
     * @return the current path to the config files
     */
    public static String getConfigPath() {
        return configPath;
    }

    /**
     * Loads a config file
     * @param configClass the class which is the "blueprint" for the config
     * @return the config instance
     * @param <C> the type of the config
     */
    public static <C extends Config> C loadConfigFile(Class<C> configClass) {
        return loadConfigFile(configClass, true);
    }

    private static <C extends Config> C loadConfigFile(Class<C> configClass, boolean firstTry) {
        if(!initialized) notInitialized();
        ObjectMapper objectMapper = new ObjectMapper();
        C defaultInstance = null;
        try {
            Constructor<C> constructor = configClass.getConstructor();
            defaultInstance = constructor.newInstance();
            C config = objectMapper.readValue(new File(getConfigPath() + defaultInstance.getName() + ".json"), configClass);
            if(config == null) {
                return defaultInstance;
            }else {
                return config;
            }
        } catch (IOException e) {
            Logger.warn(e, "Error reading config... writing new one");
            if (saveConfigFile(defaultInstance) && firstTry) {
                return loadConfigFile(configClass, false);
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            Logger.error(e);
        }
        return defaultInstance;
    }

    /**
     * Saves the config file
     * @param config the config to be saved
     * @return if saving was successful
     * @param <C> the config type
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static <C extends Config> boolean saveConfigFile(C config) {
        if(initialized) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                File file = new File(getConfigPath() + config.getName() + ".json");
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, config);
            } catch (IOException e) {
                Logger.warn(e, "Error saving config file.");
                return false;
            } catch (NullPointerException e) {
                Logger.warn(e, "Error saving config file");
                return false;
            }
            return true;
        } else {
            Logger.error("Config not initialized");
        }
        return false;
    }

    private static void notInitialized() {
        Logger.error("Config not initialized");
        ShutdownManager.shutdown(-1);
    }

}
