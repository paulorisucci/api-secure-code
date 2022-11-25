package securecodeapi.demo.exceptions.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ErrorResponseContract {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private int code;

    private String status;

    private String message;

    private Map<String, Object> invalidAttributes;

    public ErrorResponseContract() {
        this.timestamp = new Date();
    }

    public ErrorResponseContract(
            HttpStatus httpStatus,
            String message,
            Map<String, Object> invalidAttributes
    ) {
        this();
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
        this.invalidAttributes = invalidAttributes;
    }
}
