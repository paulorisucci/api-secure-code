package securecodeapi.demo.cryptography;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import securecodeapi.demo.cryptography.rsa.CustomKeyPair;

import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping(value = "/cryptography", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CryptoController {

    private CryptoService cryptoService;

    @PostMapping(value = "/sync/encrypt-values")
    public ResponseEntity<JsonNode> encryptAESValues(@RequestBody JsonNode recievedObject)
            throws NoSuchAlgorithmException {
        return ResponseEntity.ok(cryptoService.encryptAES(recievedObject));
    }

    @PostMapping(value = "/sync/decrypt-values")
    public ResponseEntity<JsonNode> decryptAESValues(@RequestBody JsonNode recievedObject)
            throws NoSuchAlgorithmException {
        return ResponseEntity.ok(cryptoService.decryptAES(recievedObject));
    }

    @PostMapping(value = "/sync/encrypt-body")
    public ResponseEntity<String> encryptAESBody(@RequestBody JsonNode recievedObject) {

        String encryptedObject = cryptoService.encryptFullObjectAES(recievedObject);
        return ResponseEntity.ok(encryptedObject);
    }

    @PostMapping(value = "/sync/decrypt-body")
    public ResponseEntity<JsonNode> decryptAESBody(@RequestBody String encryptedObject) {

        JsonNode decryptedObject = cryptoService.decryptFullObjectAES(encryptedObject);
        return ResponseEntity.ok(decryptedObject);
    }

    @PostMapping(value = "/async/generate-key")
    public ResponseEntity<CustomKeyPair> generateKeyRSA() {
        return ResponseEntity.ok(cryptoService.generateKeyPair());
    }

    @PostMapping(value = "/async/decrypt")
    public ResponseEntity<String> decryptBodyRSA(@RequestHeader("publicKey") String publicKey, @RequestBody String encryptedObject) {

        String decryptedObject = cryptoService.decryptAsync(publicKey, encryptedObject);
        return ResponseEntity.ok(decryptedObject);
    }

    @PostMapping(value = "/async/encrypt")
    public ResponseEntity<String> encryptBodyRSA(@RequestHeader("publicKey") String publicKey, @RequestBody JsonNode asyncEncryptBody) {

        String decryptedObject = cryptoService.encryptAsync(publicKey, asyncEncryptBody);
        return ResponseEntity.ok(decryptedObject);
    }
}
