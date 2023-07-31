package shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.dto.ErrorResponse;
import shopping.exception.ShoppingBaseException;

@RestControllerAdvice
public class ExceptionHandlers {

    Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

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
