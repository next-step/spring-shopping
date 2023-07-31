package shopping.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(WooWaException.class)
    public String woowaExceptionHandler(WooWaException exception) {
        //todo: 향후 수정 예정
        return "error/error";
    }
}
