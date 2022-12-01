package securecodeapi.demo.cryptography;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import securecodeapi.demo.cryptography.aes.repository.CryptoRepository;

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
}
