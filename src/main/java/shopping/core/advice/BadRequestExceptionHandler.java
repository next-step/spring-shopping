package shopping.core.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.core.exception.BadRequestException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class BadRequestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorTemplate handleStatusCodeException(BadRequestException badRequestException) {
        return new ErrorTemplate(badRequestException.getStatus(), badRequestException.getMessage());
    }
}
