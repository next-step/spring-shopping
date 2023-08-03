package shopping.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(WooWaException.class)
    public ResponseEntity<ErrorResponse> woowaExceptionHandler(WooWaException exception) {
        return ResponseEntity
            .status(exception.getHttpStatus())
            .body(new ErrorResponse(exception.getHttpStatus().value(), exception.getMessage()));
    }
}
