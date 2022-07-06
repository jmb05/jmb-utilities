package net.jmb19905.util.crypto;

public class EncryptionFactory {

    private final Encryption.KeySize size;

    public EncryptionFactory() {
        this.size = Encryption.KeySize.SIZE_256;
    }

    public EncryptionFactory(Encryption.KeySize size) {
        this.size = size;
    }

    public Encryption create() {
        return new Encryption(size);
    }

}
