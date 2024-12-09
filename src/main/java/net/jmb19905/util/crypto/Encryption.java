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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.jmb19905.util.Logger;
import net.jmb19905.util.SerializationUtility;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

/**
 * An Encryption holds the Public and Private keys for a certain Connection (Server - Client | Client - Client)
 * Each side of the interaction has a separate Encryption that will take the PublicKey of the other side to generate the Shared Key
 */

@JsonSerialize(using = Encryption.Serializer.class)
@JsonDeserialize(using = Encryption.Deserializer.class)
public class Encryption implements IEncryption {

    private PublicKey publickey;
    private PrivateKey privateKey;
    private KeyAgreement keyAgreement;
    private byte[] sharedSecret;

    private static final String ALGO = "AES";
    private static final int KEY_SIZE = 256;

    /**
     * Creates a new Encryption with unique Private and Public Keys
     */
    public Encryption() {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance("EC");
            kpg.initialize(KEY_SIZE);
            KeyPair kp = kpg.generateKeyPair();
            publickey = kp.getPublic();
            privateKey = kp.getPrivate();
            keyAgreement = KeyAgreement.getInstance("ECDH");
            keyAgreement.init(privateKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            Logger.log(e, "Error initializing Encryption", Logger.Level.ERROR);
        }
    }

    /**
     * This Constructor is for loading from a file
     *
     * @param encodedPublicKey  the PublicKey encoded in a byte-array
     * @param encodedPrivateKey the PrivateKey encoded in a byte-array
     * @param sharedSecret      the Shared Key encoded in a byte-array
     * @throws InvalidEncryptionException if the public or private key is invalid
     */
    public Encryption(byte[] encodedPublicKey, byte[] encodedPrivateKey, byte[] sharedSecret) throws InvalidEncryptionException {
        try {
            if (encodedPrivateKey != null && encodedPublicKey != null) {
                publickey = EncryptionUtility.createPublicKeyFromData(encodedPublicKey);
                privateKey = EncryptionUtility.createPrivateKeyFromData(encodedPrivateKey);
                this.sharedSecret = sharedSecret;
                try {
                    keyAgreement = KeyAgreement.getInstance("ECDH");
                    keyAgreement.init(privateKey);
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    Logger.log(e, "Error initializing EncryptedConnection", Logger.Level.ERROR);
                }
            } else {
                throw new InvalidEncryptionException("The Public or Private key is null");
            }
        } catch (NullPointerException e) {
            throw new InvalidEncryptionException("The Public or Private key is invalid");
        }
    }

    /**
     * Generates the Shared Key from the other side's PublicKey
     *
     * @param publicKey the PublicKey of the other side
     */
    public void setReceiverPublicKey(PublicKey publicKey) {
        try {
            keyAgreement.doPhase(publicKey, true);
            sharedSecret = keyAgreement.generateSecret();
        } catch (InvalidKeyException e) {
            Logger.log(e, "Invalid Key", Logger.Level.ERROR);
        }
    }

    /**
     * Encrypts a byte-array using the Shared Key
     *
     * @param in the byte-array that will be encrypted
     * @return the encrypted byte-array
     */
    public byte[] encrypt(byte[] in) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(in);
            return Base64.getEncoder().withoutPadding().encode(encVal);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            Logger.log(e, "Error encrypting", Logger.Level.ERROR);
        } catch (IllegalArgumentException e) {
            Logger.log(e, "Error encrypting! Tried to encrypt without other PublicKey", Logger.Level.WARN);
        }
        return in;
    }

    /**
     * Decrypts a byte-array using the Shared Key
     *
     * @param encryptedData the encrypted byte-array that will be decrypted
     * @return the decrypted byte-array
     */
    public byte[] decrypt(byte[] encryptedData) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
            return c.doFinal(decodedValue);
        } catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | IllegalArgumentException e) {
            Logger.error(e, "Error decrypting");
        } catch (BadPaddingException e) {
            Logger.error(e, "Error decrypting - wrong key");
        }
        return encryptedData;
    }

    /**
     * Provides the PublicKey of the Encryption endpoint
     * @return the PublicKey
     */
    public PublicKey getPublicKey() {
        return publickey;
    }

    /**
     * THIS KEY SHOULD NEVER BE USED OUTSIDE THIS DEVICE
     *
     * @return the PrivateKey
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }


    private Key generateKey() {
        return new SecretKeySpec(sharedSecret, ALGO);
    }

    /**
     * THIS KEY SHOULD NEVER BE USED OUTSIDE THIS DEVICE
     *
     * @return the Shared Key
     */
    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    /**
     * Check if the encryption endpoint has a shared secret available and can be used to encrypt and decrypt
     * @return if the endpoint is usable
     */
    public boolean isUsable() {
        return sharedSecret != null;
    }

    /**
     * Serializes the Encryption endpoint (public, private and shared key) using the Jackson Json Library
     */
    public static class Serializer extends JsonSerializer<Encryption> {
        private Serializer() {}
        @Override
        public void serialize(Encryption value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("public", SerializationUtility.encodeBinary(value.publickey.getEncoded()));
            gen.writeStringField("private", SerializationUtility.encodeBinary(value.privateKey.getEncoded()));
            if(value.sharedSecret != null) {
                gen.writeStringField("shared", SerializationUtility.encodeBinary(value.sharedSecret));
            }else {
                gen.writeStringField("shared", "null");
            }
            gen.writeEndObject();
        }
    }

    /**
     * Deserializes the Encryption endpoint (public, private and shared key) using the Jackson Json Library
     */
    public static class Deserializer extends JsonDeserializer<Encryption> {
        private Deserializer(){}
        @Override
        public Encryption deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            byte[] encodedPublic = SerializationUtility.decodeBinary(node.get("public").asText());
            byte[] encodedPrivate = SerializationUtility.decodeBinary(node.get("private").asText());
            String shared = node.get("shared").asText();
            byte[] encodedShared = null;
            if(!shared.equals("null")) {
                encodedShared = SerializationUtility.decodeBinary(shared);
            }
            try {
                return new Encryption(encodedPublic, encodedPrivate, encodedShared);
            }catch (InvalidEncryptionException e) {
                Logger.error(e);
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return "Encryption{" +
                "publicKey=" + SerializationUtility.encodeBinary(publickey.getEncoded()) +
                ", privateKey=" + SerializationUtility.encodeBinary(privateKey.getEncoded()) +
                ", sharedSecret=" + SerializationUtility.encodeBinary(sharedSecret) +
                '}';
    }
}