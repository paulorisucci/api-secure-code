package securecodeapi.demo.cryptography.aes.repository;

import org.springframework.stereotype.Repository;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Repository
public class CryptoRepository {

    private CryptoKeyManager manager;

    public CryptoRepository() throws NoSuchAlgorithmException, IOException {
        this.manager = new CryptoKeyManager();
    }

    public SecretKey getSecretKey() {
        return this.manager.getSecretKey();
    }

    public IvParameterSpec getIvParameter() {
        return this.manager.getIvParameterSpec();
    }
}
