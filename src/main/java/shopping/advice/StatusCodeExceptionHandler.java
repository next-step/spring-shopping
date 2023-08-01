package shopping.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.domain.exception.StatusCodeException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class StatusCodeExceptionHandler {

    @ExceptionHandler(StatusCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorTemplate handleStatusCodeException(StatusCodeException statusCodeException) {
        return new ErrorTemplate(statusCodeException.getStatus(), statusCodeException.getMessage());
    }
}
