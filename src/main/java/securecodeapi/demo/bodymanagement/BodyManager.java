package securecodeapi.demo.bodymanagement;

import com.fasterxml.jackson.databind.JsonNode;
import securecodeapi.demo.exceptions.InvalidObjectException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BodyManager {

    public static HashMap<String, Object> toMap(JsonNode node) {
        Object result = BodyConverter.convert(node);

        if(Objects.isNull(result) || !Objects.equals(result.getClass(), HashMap.class)) {
            throw new InvalidObjectException("O corpo da requisição não é válido");
        }
        return (HashMap<String, Object>) result;
    }

    public static ArrayList<?> toList(JsonNode node) {
        Object result = BodyConverter.convert(node);

        if(Objects.isNull(result) || !Objects.equals(result.getClass(), List.class)) {
            throw new InvalidObjectException("O corpo da requisição não é válido");
        }
        return (ArrayList<?>) result;
    }
}
