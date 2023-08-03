package shopping.exception.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shopping.dto.response.ErrorResponse;
import shopping.exception.AuthException;
import shopping.exception.ShoppingBaseException;

@ControllerAdvice
public class ExceptionHandlers {

    Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler({AuthException.class, SignatureException.class, ExpiredJwtException.class})
    public String handleAuthException(Exception e) {
        log.error(e.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageConversionException(
            HttpMessageConversionException e) {

        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse("입력이 잘못되었습니다."));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse("입력이 잘못되었습니다."));
    }

    @ExceptionHandler(ShoppingBaseException.class)
    public ResponseEntity<ErrorResponse> handleShoppingBaseException(ShoppingBaseException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        log.error("예상치 못한 에러가 발생했습니다.", e);
        return ResponseEntity.internalServerError().body(new ErrorResponse());
    }
}
