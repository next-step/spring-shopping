package shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(WooWaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String woowaExceptionHandler(WooWaException exception) {
        //todo: 향후 수정 예정
        return "error/error";
    }
}
