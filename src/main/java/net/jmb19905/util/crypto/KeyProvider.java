package net.jmb19905.util.crypto;

import javax.crypto.SecretKey;

public interface KeyProvider {
    SecretKey get();
}
