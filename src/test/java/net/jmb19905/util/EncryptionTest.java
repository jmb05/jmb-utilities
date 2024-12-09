package net.jmb19905.util;

import net.jmb19905.util.crypto.Encryption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class EncryptionTest {

    @Test
    public void testEncryption() {
        Encryption encryption1 = new Encryption();
        Encryption encryption2 = new Encryption();

        encryption1.setReceiverPublicKey(encryption2.getPublicKey());
        encryption2.setReceiverPublicKey(encryption1.getPublicKey());

        Random random = new Random();
        byte[] testBytes = new byte[random.nextInt(100)];
        random.nextBytes(testBytes);

        byte[] b = encryption1.encrypt(testBytes);
        byte[] decrypted = encryption2.decrypt(b);

        Assertions.assertArrayEquals(testBytes, decrypted);
    }

}
