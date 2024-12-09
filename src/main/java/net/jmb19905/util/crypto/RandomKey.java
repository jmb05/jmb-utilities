package net.jmb19905.util.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class RandomKey implements KeyProvider {

    private final int n;

    public RandomKey(int n) {
        this.n = n;
    }

    @Override
    public SecretKey get() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(n);
            return generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
