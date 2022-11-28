package securecodeapi.demo.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@JsonInclude(Include.NON_NULL)
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
