package shopping.ui.advice;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.dto.response.ErrorResponse;
import shopping.exception.AuthException;
import shopping.exception.ShoppingException;
import shopping.exception.TokenException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(TokenException.class)
    ResponseEntity<ErrorResponse> catchTokenException(TokenException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()),
            HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthException.class)
    ResponseEntity<ErrorResponse> catchAuthException(AuthException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()),
            HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ShoppingException.class)
    ResponseEntity<ErrorResponse> catchShoppingException(ShoppingException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorResponse> catchHttpMessageNotReadableException(
        HttpMessageNotReadableException exception) {
        String message = "유효하지 않은 입력 값입니다.";
        if (Objects.nonNull(exception.getRootCause())) {
            message = exception.getRootCause().getMessage();
        }
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(message));
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> catchRuntimeException(RuntimeException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.internalServerError()
            .body(new ErrorResponse("알 수 없는 에러가 발생하였습니다."));
    }
}
