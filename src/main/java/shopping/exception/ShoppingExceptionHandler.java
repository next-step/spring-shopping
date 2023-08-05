package shopping.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShoppingExceptionHandler {

    @ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithShoppingApiException(final ShoppingException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithAuthException(final AuthException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiExceptionResponse.from(e.getMessage()));
    }
}
