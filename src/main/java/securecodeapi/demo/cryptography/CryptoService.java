package securecodeapi.demo.cryptography;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import securecodeapi.demo.cryptography.aes.repository.CryptoRepository;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class CryptoService {

    private final CryptoRepository cryptoRepository;

    public HashMap<String, String> encryptAES(HashMap<String, String> recievedObject) throws NoSuchAlgorithmException {

        return ObjectCrypter.encryptObjectValuesUsingAes("AES/CBC/PKCS5Padding", recievedObject,
                this.cryptoRepository.getSecretKey(), cryptoRepository.getIvParameter());
    }

    public HashMap<String, String> decryptAES(HashMap<String, String> recievedObject) throws NoSuchAlgorithmException {

        return ObjectCrypter.decryptObjectValuesUsingAes("AES/CBC/PKCS5Padding", recievedObject,
                this.cryptoRepository.getSecretKey(), cryptoRepository.getIvParameter());
    }
}
