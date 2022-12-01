package securecodeapi.demo.cryptography.aes;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

public class AesCrypterTest {

    private final int BLOCK_SIZE = 128;

    @Test
    void shouldEncryptAndDecryptStringSuccessfully() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        final var input = "any string";
        final var secretKey = AesKeyGenerator.generateKey(BLOCK_SIZE);
        final var iv = AesKeyGenerator.generateIv();

        final var cipherText = AesCrypter.encrypt(input, secretKey, iv);
        final var plainText = AesCrypter.decrypt(cipherText, secretKey, iv);

        assertThat(plainText).isEqualTo(input);
    }
}
