package securecodeapi.demo.cryptography;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import securecodeapi.demo.cryptography.aes.repository.CryptoRepository;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@Service
public class CryptoService {

    private final CryptoRepository cryptoRepository;

    private final ObjectCrypter objectCrypter;

    public CryptoService(CryptoRepository repository) {
        this.cryptoRepository = repository;
        this.objectCrypter = new ObjectCrypter(new ObjectMapper());
    }

    public JsonNode encryptAES(JsonNode recievedObject) {

        return objectCrypter.encryptObjectValuesUsingAes(recievedObject, this.cryptoRepository.getSecretKey(),
                cryptoRepository.getIvParameter());
    }

    public JsonNode decryptAES(JsonNode recievedObject) {

        return objectCrypter.decryptObjectValuesUsingAes(recievedObject, this.cryptoRepository.getSecretKey(),
                cryptoRepository.getIvParameter());
    }

    public String encryptFullObjectAES(JsonNode recievedObject) {
        SecretKey secretKey = cryptoRepository.getSecretKey();
        IvParameterSpec ivParameterSpec = cryptoRepository.getIvParameter();
        return this.objectCrypter.encryptObjectUsingAes(recievedObject, secretKey, ivParameterSpec);
    }

    public JsonNode decryptFullObjectAES(String encryptedObject) {
        SecretKey secretKey = cryptoRepository.getSecretKey();
        IvParameterSpec ivParameterSpec = cryptoRepository.getIvParameter();

        return this.objectCrypter.decryptObjectUsingAes(encryptedObject, secretKey, ivParameterSpec);
    }
}
