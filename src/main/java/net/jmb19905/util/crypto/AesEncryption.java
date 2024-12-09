package net.jmb19905.util.crypto;

import net.jmb19905.util.Logger;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Provides Encryption according to the AES (Advanced Encryption Standard)
 */
public class AesEncryption implements IEncryption {

    private final SecretKey key;

    /**
     * Create an object of type AesEncryption
     * @param keyProvider the KeyProvider which supplies the cryptographic keys
     */
    public AesEncryption(KeyProvider keyProvider) {
        key = keyProvider.get();
    }

    @Override
    public byte[] encrypt(byte[] in) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(in);
            return Base64.getEncoder().withoutPadding().encode(encVal);
        } catch (BadPaddingException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException e) {
            Logger.log(e, "Error encrypting", Logger.Level.ERROR);
        } catch (IllegalArgumentException e) {
            Logger.log(e, "Error encrypting! Invalid Key", Logger.Level.WARN);
        }
        return in;
    }

    @Override
    public byte[] decrypt(byte[] in) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedValue = Base64.getDecoder().decode(in);
            return c.doFinal(decodedValue);
        } catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | IllegalArgumentException e) {
            Logger.error(e, "Error decrypting");
        } catch (BadPaddingException e) {
            Logger.error(e, "Error decrypting - wrong key");
        }
        return in;
    }
}
