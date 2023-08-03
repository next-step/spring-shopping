package shopping.ui.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.dto.ErrorResponse;
import shopping.exception.AuthException;
import shopping.exception.ShoppingException;
import shopping.exception.TokenException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(TokenException.class)
    ResponseEntity<ErrorResponse> catchTokenException(TokenException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthException.class)
    ResponseEntity<ErrorResponse> catchAuthException(AuthException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ShoppingException.class)
    ResponseEntity<ErrorResponse> catchShoppingException(ShoppingException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }
}
