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

package net.jmb19905.util.bootstrapping;

import net.jmb19905.util.Logger;
import net.jmb19905.util.ShutdownManager;
import net.jmb19905.util.config.Config;
import net.jmb19905.util.config.ConfigManager;

@SuppressWarnings("unused")
public class Bootstrap {

    private final String[] args;
    private String programSignature = null;
    private String loggerSignature = null;
    private boolean useVersion = false;
    private boolean useConfig = false;
    private Class<? extends Config> configClass = null;

    protected Version version;
    protected DeployState deployState;
    protected Config config;

    private boolean bootStrapped = false;

    protected Bootstrap(String[] args) {
        this.args = args;
    }

    public static Bootstrap newBootstrap(String[] args) {
        return new Bootstrap(args);
    }

    public Bootstrap loggerSignature(String loggerSignature) {
        if(!bootStrapped) {
            this.loggerSignature = loggerSignature;
        }
        return this;
    }

    public Bootstrap useVersion() {
        if(!bootStrapped) {
            useVersion = true;
        }
        return this;
    }

    public Bootstrap config(String programSignature, Class<? extends Config> configClass) {
        if(!bootStrapped) {
            this.useConfig = true;
            this.programSignature = programSignature;
            this.configClass = configClass;
        }
        return this;
    }

    public Bootstrap bootstrap() {
        if(bootStrapped) {
            Logger.warn("Bootstrap cannot be executed twice");
            return this;
        }else bootStrapped = true;
        if (args.length > 0) {
            deployState = DeployState.fromString(args[0]);
        }
        if(loggerSignature != null) {
            Logger.initLogFile(loggerSignature);
            ShutdownManager.addCleanupLast(() -> {
                if(useConfig) {
                    ConfigManager.saveConfigFile(config);
                }
                Logger.close();
            });
        }if(useVersion) {
            version = Version.loadVersion(deployState);
        }
        if(useConfig) {
            ConfigManager.init(programSignature, deployState);
            config = ConfigManager.loadConfigFile(configClass);
            Logger.info("Loaded configs");
        }
        return this;
    }

    public Version getVersion() {
        return version;
    }

    public DeployState getDeployState() {
        return deployState;
    }

    public Config getConfig() {
        return config;
    }
}
