package net.jmb19905.util.crypto;

/**
 * Interface for implementing different encryption algorithms
 */
public interface IEncryption {

    /**
     * Encrypts a byte array
     * @param in the input data
     * @return the encrypted data
     */
    byte[] encrypt(byte[] in);

    /**
     * Decrypts a byte array
     * @param in the encrypted data
     * @return the decrypted data
     */
    byte[] decrypt(byte[] in);

}