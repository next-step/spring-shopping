package shopping.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(WooWaException.class)
    public ResponseEntity<ErrorResponse> woowaExceptionHandler(WooWaException exception) {
        return ResponseEntity
            .status(exception.getHttpStatus())
            .body(new ErrorResponse(exception.getHttpStatus().value(), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception exception) {
        logger.error("[Exception] message = {}, stack trace = {}", exception.getMessage(), exception.getStackTrace());
        return new ErrorResponse(
            INTERNAL_SERVER_ERROR.value(),
            "서버 내부에서 오류가 발생했습니다. 불편을 드려 죄송합니다."
        );
    }
}
