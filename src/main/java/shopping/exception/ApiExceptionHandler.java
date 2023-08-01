package shopping.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ShoppingApiException.class)
    public ResponseEntity<ApiExceptionResponse> exceptionHandleWithShoppingException(final ShoppingApiException e) {
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
