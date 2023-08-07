package shopping.ui.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.dto.response.ErrorResponse;
import shopping.exception.AuthException;
import shopping.exception.ShoppingException;
import shopping.exception.TokenException;

@RestControllerAdvice
public class GlobalControllerAdvice {

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

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> catchRuntimeException() {
        return ResponseEntity.internalServerError()
            .body(new ErrorResponse("알 수 없는 에러가 발생하였습니다."));
    }
}
