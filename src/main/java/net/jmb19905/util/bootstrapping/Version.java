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

public class Version {

    private int major;
    private int minor;
    private int patch;
    private Type type;
    private int typeVersion;

    public Version(String versionString) {
        if(versionString == null) invalidate();
        else {
            String[] parts = versionString.split("-");
            String[] semanticParts = parts[0].split("\\.");
            this.major = Integer.parseInt(semanticParts[0]);
            this.minor = Integer.parseInt(semanticParts[1]);
            this.patch = Integer.parseInt(semanticParts[2]);

            Type type1 = Type.STABLE;
            int typeVersion1 = 0;
            try {
                if (parts[1].startsWith("alpha")) {
                    type1 = Type.ALPHA;
                    parts[1] = parts[1].replaceAll("alpha", "");
                } else if (parts[1].startsWith("beta")) {
                    type1 = Type.BETA;
                    parts[1] = parts[1].replaceAll("beta", "");
                } else if (parts[1].startsWith("rc")) {
                    type1 = Type.RELEASE_CANDIDATE;
                    parts[1] = parts[1].replaceAll("rc", "");
                }
                typeVersion1 = Integer.parseInt(parts[1]);
            } catch (ArrayIndexOutOfBoundsException e) {/*Version is Stable*/}
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
     * @param isDev if the instance is running in a development environment
     * @return the version
     */
    public static Version loadVersion(boolean isDev) {
        String versionAsString = "0.0.0";
        if (isDev) {
            try {
                versionAsString = ResourceUtility.readResourceProperties("version.properties").getProperty("version");
            } catch (NullPointerException e) {
                Logger.error(e, "Error reading version");
            }
        } else {
            versionAsString = Version.class.getPackage().getImplementationVersion();
        }
        return new Version(versionAsString);
    }

    private void invalidate() {
        this.major = 0;
        this.minor = 0;
        this.patch = 0;
        this.type = Type.STABLE;
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
        String typeString = "";
        if (type != Type.STABLE) {
            if (type == Type.ALPHA) {
                typeString = "-alpha";
            } else if (type == Type.BETA) {
                typeString = "-beta";
            } else if (type == Type.RELEASE_CANDIDATE) {
                typeString = "-rc";
            }
            typeString = typeString.concat(typeVersion + "");
        }
        return semanticString + typeString;
    }

    public enum Type {ALPHA, BETA, RELEASE_CANDIDATE, STABLE}

}
