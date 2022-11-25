package securecodeapi.demo.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class InvalidObjectException extends RuntimeException {

    private Map<String, Object> invalidAttributes;

    public InvalidObjectException() {
        super();
    }

    public InvalidObjectException(String message) {
        super(message);
    }

    public InvalidObjectException(String message, Map<String, Object> invalidAttributes) {
        super(message);
        this.invalidAttributes = invalidAttributes;
    }
}
