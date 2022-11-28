package securecodeapi.demo.cryptography;

import securecodeapi.demo.cryptography.aes.AesCrypter;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.HashMap;
import java.util.Map;

public class ObjectCrypter {

    public static HashMap<String, String> encryptObjectValuesUsingAes(String algorithm, Map<String, String> recievedObject, SecretKey secretKey,
                                                                      IvParameterSpec ivParameterSpec) {
        final var encryptedObject = new HashMap<>(recievedObject);

        encryptedObject.replaceAll((key, value) -> {
            try {
                return AesCrypter.encrypt(algorithm, value, secretKey, ivParameterSpec);
            } catch (Exception e) {
                e.printStackTrace();
                return value;
            }
        });

        return encryptedObject;
    }

    public static HashMap<String, String> decryptObjectValuesUsingAes(String algorithm, Map<String, String> recievedObject, SecretKey secretKey,
                                                                      IvParameterSpec ivParameterSpec) {

        final var decryptedObject = new HashMap<>(recievedObject);

        decryptedObject.replaceAll((key, value) -> {
            try {
                return AesCrypter.decrypt(algorithm, value, secretKey, ivParameterSpec);
            } catch (Exception e) {
                e.printStackTrace();
                return value;
            }
        });

        return decryptedObject;
    }
}
