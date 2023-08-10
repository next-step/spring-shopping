package shopping.ui.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.dto.ErrorResponse;
import shopping.exception.AuthException;
import shopping.exception.CurrencyLayerException;
import shopping.exception.ExternalApiException;
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

    @ExceptionHandler(ExternalApiException.class)
    ResponseEntity<ErrorResponse> catchExternalApiException(ExternalApiException exception) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(CurrencyLayerException.class)
    ResponseEntity<ErrorResponse> catchCurrencyLayerException(CurrencyLayerException exception) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> catchException() {
        return ResponseEntity.internalServerError().body(new ErrorResponse("서버 내부에서 오류가 발생했습니다"));
    }
}
