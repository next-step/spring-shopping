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

    static class ApiExceptionResponse {

        private final String message;

        private ApiExceptionResponse(final String message) {
            this.message = message;
        }

        public static ApiExceptionResponse from(final String message) {
            return new ApiExceptionResponse(message);
        }

        public String getMessage() {
            return this.message;
        }
    }
}
