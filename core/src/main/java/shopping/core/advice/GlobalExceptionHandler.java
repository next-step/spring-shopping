package shopping.core.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.core.util.ErrorTemplate;

@Order
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(
        GlobalExceptionHandler.class.getSimpleName());

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorTemplate handleStatusCodeException(Exception exception) {
        logger.error(exception.getMessage());
        return new ErrorTemplate("INTERNAL-SERVER-ERROR");
    }

}
