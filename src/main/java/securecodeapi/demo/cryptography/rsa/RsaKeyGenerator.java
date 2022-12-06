package securecodeapi.demo.cryptography.rsa;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.Base64;

public class RsaKeyGenerator {

    private static final String ALGORITHM = "RSA";

    private static final Integer KEY_SIZE = 1024;

    public KeyPair generateKeyPair() {
        try {
            final var keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);

            final var keyPair = keyPairGenerator.generateKeyPair();

            return keyPair;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String plainText, PublicKey key) {

        try {
            final var cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Encrypt error");
        }
    }

    public static String decrypt(String cipherText, PrivateKey key) {

        try {
            final var cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
            byte[] decryptedText = cipher.doFinal(cipherTextBytes);

            return new String(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Decrypt error");
        }
    }
    public static String encodePublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String encodePrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static PublicKey decodePublicKey(String key) {
        try {
            final byte[] base64Decoded = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(base64Decoded);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("erro na decodificação da chave");
        }
    }

    public static PrivateKey decodePrivatekey(String key) {
        try {
            final byte[] base64Decoded = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(base64Decoded);
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("erro na decodificação da chave");
        }
    }

}
