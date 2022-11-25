package securecodeapi.demo.cryptography.aes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class AesKeyGenerator {

    public static SecretKey generateKey(int keySize) throws NoSuchAlgorithmException {
        final var keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keySize);

        return keyGenerator.generateKey();
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

}
