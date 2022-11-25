package securecodeapi.demo.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import securecodeapi.demo.exceptions.InvalidObjectException;

@ControllerAdvice
public class CustomHandlerAdvice {

    @ExceptionHandler(InvalidObjectException.class)
    public ResponseEntity<ErrorResponseContract> handleInvalidObjectException(InvalidObjectException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new ErrorResponseContract(status, e.getMessage(), e.getInvalidAttributes()), status);
    }
}
