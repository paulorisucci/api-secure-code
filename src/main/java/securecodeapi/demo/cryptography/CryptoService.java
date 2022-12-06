package securecodeapi.demo.cryptography;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import securecodeapi.demo.cryptography.aes.repository.CryptoRepository;
import securecodeapi.demo.cryptography.rsa.CustomKeyPair;
import securecodeapi.demo.cryptography.rsa.RsaKeyGenerator;
import securecodeapi.demo.cryptography.rsa.repository.AsyncKeyRepository;
import securecodeapi.demo.firestore.FirebaseInitialization;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@Service
public class CryptoService {

    private final CryptoRepository cryptoRepository;

    private final ObjectCrypter objectCrypter;

    private final RsaKeyGenerator rsaKeyGenerator;

    private final AsyncKeyRepository asyncKeyRepository;

    public CryptoService(CryptoRepository repository, FirebaseInitialization firebaseInitialization) {
        this.cryptoRepository = repository;
        this.objectCrypter = new ObjectCrypter(new ObjectMapper());
        this.rsaKeyGenerator = new RsaKeyGenerator();
        this.asyncKeyRepository = new AsyncKeyRepository(firebaseInitialization);
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

    public CustomKeyPair generateKeyPair() {
        final var keyPair = this.rsaKeyGenerator.generateKeyPair();
        final var customKeyPair = new CustomKeyPair(keyPair);
        this.asyncKeyRepository.save(customKeyPair);
        return customKeyPair;
    }

    public String encryptAsync(String publicKeyText, JsonNode node) {
        final var publicKey = RsaKeyGenerator.decodePublicKey(publicKeyText);
        return RsaKeyGenerator.encrypt(node.toString(), publicKey);
    }

    public String decryptAsync(String publicKeyText, String encryptedText) {
        final var publicKey = RsaKeyGenerator.decodePublicKey(publicKeyText);
        final var customKeyPair = asyncKeyRepository.findByPublicKey(publicKey);

        final var privateKey = RsaKeyGenerator.decodePrivatekey(customKeyPair.getPrivateKey());

        return RsaKeyGenerator.decrypt(encryptedText, privateKey);
    }
}
