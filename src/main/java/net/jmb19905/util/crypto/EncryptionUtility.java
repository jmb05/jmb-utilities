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

package net.jmb19905.util.crypto;

import net.jmb19905.util.Logger;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Utility methods used for Encryption
 */
public class EncryptionUtility {

    private EncryptionUtility() {}

    /**
     * Decodes a PublicKey from a byte-array
     *
     * @param encodedKey the key encoded as byte-array
     * @return the decoded PublicKey
     */
    public static PublicKey createPublicKeyFromData(byte[] encodedKey) {
        try {
            KeyFactory factory = KeyFactory.getInstance("EC");
            return factory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            Logger.warn(e, "Error retrieving PublicKey");
            return null;
        }
    }

    /**
     * Decodes a PrivateKey from a byte-array
     *
     * @param encodedKey the key encoded as byte-array
     * @return the decoded PublicKey
     */
    public static PrivateKey createPrivateKeyFromData(byte[] encodedKey) {
        try {
            KeyFactory factory = KeyFactory.getInstance("EC");
            return factory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            Logger.log(e, "Error retrieving PrivateKey", Logger.Level.WARN);
            return null;
        }
    }

    /**
     * Encrypts a String in the UTF-8 encoding
     *
     * @param encryption the Encryption that will encrypt the String
     * @param value      the String to be encrypted
     * @return the encrypted String
     */
    public static String encryptString(IEncryption encryption, String value) {
        return new String(encryption.encrypt(value.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    /**
     * Decrypts a String in the UTF-8 encoding
     *
     * @param encryption the Encryption that will decrypt the String
     * @param value      the String to be decrypted
     * @return the decrypted String
     */
    public static String decryptString(IEncryption encryption, String value) {
        return new String(encryption.decrypt(value.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    /**
     * Encrypts a 2-Dimensional array of bytes
     *
     * @param encryption the EncryptedConnection that will be used for encryption
     * @param data       the 2D byte-array
     * @return an encrypted 2D byte-array
     */
    public static byte[][] encrypt2DBytes(IEncryption encryption, byte[][] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = encryption.encrypt(data[i]);
        }
        return data;
    }

    /**
     * Decrypts a 2-Dimensional array of bytes
     *
     * @param encryption the EncryptedConnection that will be used for decryption
     * @param data       the 2D byte-array
     * @return a decrypted 2D byte-array
     */
    public static byte[][] decrypt2DBytes(IEncryption encryption, byte[][] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = encryption.decrypt(data[i]);
        }
        return data;
    }

}
