package securecodeapi.demo.bodymanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import securecodeapi.demo.exceptions.InvalidObjectException;

import java.util.HashMap;
import java.util.List;

public class BodyConverter {

    public static Object convert(JsonNode jsonNode) {
        final var nodeType = jsonNode.getNodeType();

        switch (nodeType) {
            case ARRAY -> {
                return toList(jsonNode);
            } case OBJECT -> {
                return toMap(jsonNode);
            } default -> {
                return jsonNode;
            }
        }
    }

    static HashMap<?, ?> toMap(JsonNode jsonNode) {
        try {
            return new ObjectMapper().treeToValue(jsonNode, HashMap.class);
        } catch (JsonProcessingException exc) {
            exc.printStackTrace();
            throw new InvalidObjectException("O corpo da requisição não é válido");
        }
    }

    static List<?> toList(JsonNode jsonNode) {
        try {
            return new ObjectMapper().treeToValue(jsonNode, List.class);
        } catch (JsonProcessingException exc) {
            exc.printStackTrace();
            throw new InvalidObjectException("O corpo da requisição não é válido");
        }
    }
}
