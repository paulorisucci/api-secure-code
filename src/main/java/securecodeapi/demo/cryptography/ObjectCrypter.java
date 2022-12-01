package securecodeapi.demo.cryptography;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import lombok.AllArgsConstructor;
import securecodeapi.demo.bodymanagement.BodyManager;
import securecodeapi.demo.cryptography.aes.AesCrypter;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@AllArgsConstructor
public class ObjectCrypter {

    private final ObjectMapper mapper;

    public JsonNode encryptObjectValuesUsingAes(JsonNode recievedObject, SecretKey secretKey,
                                                                      IvParameterSpec ivParameterSpec) {

        if(JsonNodeType.ARRAY.equals(recievedObject.getNodeType())) {

            ArrayNode arrayNode = this.mapper.createArrayNode();
            recievedObject.forEach( nodeObj -> arrayNode.add(this.encryptObjectValuesUsingAes(nodeObj, secretKey, ivParameterSpec)));
            return arrayNode;
        }

        final var encryptedObject = BodyManager.toMapWithStringValues(recievedObject);

        encryptedObject.replaceAll((key, value) -> {
            try {
                return AesCrypter.encrypt(value, secretKey, ivParameterSpec);
            } catch (Exception e) {
                e.printStackTrace();
                return value;
            }
        });

        return this.mapper.convertValue(encryptedObject, JsonNode.class);

    }

    public JsonNode decryptObjectValuesUsingAes(JsonNode recievedObject, SecretKey secretKey,
                                                                      IvParameterSpec ivParameterSpec) {

        if(JsonNodeType.ARRAY.equals(recievedObject.getNodeType())) {

            ArrayNode arrayNode = this.mapper.createArrayNode();
            recievedObject.forEach( nodeObj -> arrayNode.add(decryptObjectValuesUsingAes(nodeObj, secretKey, ivParameterSpec)));
            return arrayNode;
        }

        final var decryptedObject = BodyManager.toMapWithStringValues(recievedObject);

        decryptedObject.replaceAll((key, value) -> {
            try {
                return AesCrypter.decrypt(value, secretKey, ivParameterSpec);
            } catch (Exception e) {
                e.printStackTrace();
                return value;
            }
        });

        return this.mapper.convertValue(decryptedObject, JsonNode.class);
    }
}
