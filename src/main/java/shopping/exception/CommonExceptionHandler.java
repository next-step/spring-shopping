package shopping.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(WooWaException.class)
    public ResponseEntity<ErrorResponse> woowaExceptionHandler(WooWaException exception) {
        return ResponseEntity
            .status(exception.getHttpStatus())
            .body(new ErrorResponse(exception.getHttpStatus().value(), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception exception) {
        exception.printStackTrace(); // lombok 적용시 log로 변환
        return new ErrorResponse(
            INTERNAL_SERVER_ERROR.value(),
            "서버 내부에서 오류가 발생했습니다. 불편을 드려 죄송합니다."
        );
    }
}
