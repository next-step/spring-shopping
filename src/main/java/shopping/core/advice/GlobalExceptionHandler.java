package shopping.core.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order
@RestControllerAdvice
class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getSimpleName());

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorTemplate handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        logger.error(exception.getMessage());
        return new ErrorTemplate("DATABASE-501", "DATABASE ERROR");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorTemplate handleStatusCodeException(Exception exception) {
        logger.error(exception.getMessage());
        return new ErrorTemplate("SERVER-501", "INTERNAL SERVER ERROR");
    }
}
