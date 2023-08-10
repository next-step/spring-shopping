package shopping.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @ExceptionHandler(ShoppingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleShoppingException(final ShoppingException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        return new ErrorResponse(getRootMessage(exception));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception exception) {
        log.error(exception.toString());
        return new ErrorResponse(exception.getMessage());
    }

    private static String getRootMessage(final HttpMessageNotReadableException exception) {
        if (exception.getRootCause() == null) {
            return ErrorType.SERVER_ERROR.getMessage();
        }
        return exception.getRootCause().getMessage();
    }
}
