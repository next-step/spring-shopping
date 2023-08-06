package shopping.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionType {

    HttpStatus getHttpStatus();

    String getMessage();

}
