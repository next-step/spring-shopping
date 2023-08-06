package shopping.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shopping.exception.dto.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ExceptionResponse> handleShoppingException(
        final ShoppingException exception
    ) {
        logger.error("message : {}", exception.getMessage());

        return ResponseEntity.badRequest().body(new ExceptionResponse(exception));
    }

    @ExceptionHandler(ShoppingAuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleShoppingAuthenticationException(
        final ShoppingAuthenticationException exception
    ) {
        logger.error("message : {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ExceptionResponse(exception));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        logger.error("message : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ExceptionResponse("서버 내부 예외가 발생했습니다."));
    }
}
