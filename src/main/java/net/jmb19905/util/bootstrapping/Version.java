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
import net.jmb19905.util.ResourceUtility;

@SuppressWarnings("unused")
public class Version {

    private int major;
    private int minor;
    private int patch;
    private Type type;
    private int typeVersion;

    public Version(String versionString) {
        if(versionString == null || versionString.isBlank()) invalidate();
        else {
            String[] parts = versionString.split("-");
            try {
                String[] semanticParts = parts[0].split("\\.");
                this.major = Integer.parseInt(semanticParts[0]);
                this.minor = Integer.parseInt(semanticParts[1]);
                this.patch = Integer.parseInt(semanticParts[2]);
            }catch (ArrayIndexOutOfBoundsException e) {
                invalidate();
            }

            Type type1 = Type.INVALID;
            int typeVersion1 = 0;
            if (parts.length == 1) {
                type1 = Type.STABLE;
            } else if (parts[1].startsWith("alpha")) {
                type1 = Type.ALPHA;
                parts[1] = parts[1].replaceAll("alpha", "");
                typeVersion1 = Integer.parseInt(parts[1]);
            } else if (parts[1].startsWith("beta")) {
                type1 = Type.BETA;
                parts[1] = parts[1].replaceAll("beta", "");
                typeVersion1 = Integer.parseInt(parts[1]);
            } else if (parts[1].startsWith("rc")) {
                type1 = Type.RELEASE_CANDIDATE;
                parts[1] = parts[1].replaceAll("rc", "");
                typeVersion1 = Integer.parseInt(parts[1]);
            }
            this.type = type1;
            this.typeVersion = typeVersion1;
        }
    }

    public Version(int major, int minor, int patch, Type type, int typeVersion) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.type = type;
        this.typeVersion = typeVersion;
    }

    /**
     * Loads the version from the version.properties file if the instance is running in a development environment and
     * from the jars manifest if the instance is running as standalone program
     *
     * @param deployState if the instance is running in a development environment
     * @return the version
     */
    public static Version loadVersion(DeployState deployState) {
        return new Version(switch (deployState) {
            case DEV, DEV_DEPLOY, DEPLOY -> {
                try {
                    yield ResourceUtility.readResourceProperties("version.properties").getProperty("version");
                } catch (NullPointerException e) {
                    Logger.error(e, "Error reading version");
                }
                yield "0.0.0-invalid";
            }
            default -> Version.class.getPackage().getImplementationVersion();
        });
    }

    private void invalidate() {
        this.major = 0;
        this.minor = 0;
        this.patch = 0;
        this.type = Type.INVALID;
        this.typeVersion = 0;
    }

    public boolean isIncompatible(Version version) {
        if (this == version) {
            return false;
        }
        if (this.major != version.major) {
            return true;
        }
        if (this.minor != version.minor) {
            return true;
        }
        if (this.type != version.type) {
            return true;
        }
        return type != Type.STABLE && this.typeVersion != version.typeVersion;
    }

    @Override
    public String toString() {
        String semanticString = major + "." + minor + "." + patch;
        String typeString = switch (type) {
            case ALPHA -> "-alpha" + typeVersion;
            case BETA -> "-beta" + typeVersion;
            case RELEASE_CANDIDATE -> "-rc" + typeVersion;
            case STABLE -> "";
            case INVALID -> "-invalid";
        };
        return semanticString + typeString;
    }

    public enum Type {ALPHA, BETA, RELEASE_CANDIDATE, STABLE, INVALID}

}
