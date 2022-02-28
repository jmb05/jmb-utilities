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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordHashing {

    private static final SecureRandom random = new SecureRandom();
    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Logger.error(e);
        }
    }

    public static byte[] generateSalt() {
        return generateSalt(64);
    }

    public static byte[] generateSalt(int len) {
        int length = len;
        if(length < 16) length = 16;
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] hashPassword(String password, byte[] salt) {
        md.update(salt);
        byte[] hashed = md.digest(password.getBytes(StandardCharsets.UTF_8));
        md.reset();
        return hashed;
    }

    public static boolean checkPassword(String toCheck, byte[] hashedSalted, byte[] salt) {
        return Arrays.equals(hashPassword(toCheck, salt), hashedSalted);
    }

}
