package shopping.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        final ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
        System.out.println(e.getClass());
        e.printStackTrace();
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.from(errorCode));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleApplicationException(ShoppingException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.from(errorCode));
    }
}
