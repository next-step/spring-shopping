package shopping.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shopping.exception.dto.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ExceptionResponse> handleShoppingException(
        final ShoppingException exception
    ) {

        return ResponseEntity.badRequest().body(new ExceptionResponse(exception));
    }
}
