package shopping.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException exception) {
        log.error("예상하지 못한 에러입니다.", exception);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "예상하지 못한 에러입니다."));
    }

    @ExceptionHandler(WooWaException.class)
    public ResponseEntity<ErrorResponse> woowaExceptionHandler(WooWaException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
            .status(exception.getHttpStatus())
            .body(new ErrorResponse(exception.getHttpStatus().value(), exception.getMessage()));
    }
}
