package shopping.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ErrorResponse> handleShoppingException(final ShoppingException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatus());
    }
}
