package shopping.exception;

import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(ShoppingException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandleWithShoppingException(final ShoppingException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse exceptionHandleWithSQLException() {
        return ExceptionResponse.from("서버 내부에서 오류가 발생했습니다.");
    }

}
