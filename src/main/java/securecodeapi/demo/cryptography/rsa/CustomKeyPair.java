package securecodeapi.demo.cryptography.rsa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.security.KeyPair;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class CustomKeyPair {

    private String publicKey;

    @JsonIgnore
    private String privateKey;

    private CustomKeyPair(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public CustomKeyPair(KeyPair keyPair) {
        this.privateKey = RsaKeyGenerator.encodePrivateKey(keyPair.getPrivate());
        this.publicKey = RsaKeyGenerator.encodePublicKey(keyPair.getPublic());
    }

    public CustomKeyPair(Map<String, String> pairMap) {
        this.publicKey = pairMap.get("publicKey");
        this.privateKey = pairMap.get("privateKey");
    }

    public Map<String, String> getMapToSave() {
        return Stream.of(new Object[][]{
                {"privateKey", this.privateKey},
                {"publicKey", this.publicKey},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));
    }

    public CustomKeyPair(String publicKey) {
        this.publicKey = publicKey;
    }

}
