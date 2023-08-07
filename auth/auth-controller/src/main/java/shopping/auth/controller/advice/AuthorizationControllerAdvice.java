package shopping.auth.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping.auth.domain.exception.InvalidTokenException;
import shopping.core.util.ErrorTemplate;

@RestControllerAdvice
public class AuthorizationControllerAdvice {

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorTemplate handleUnAuthorized(InvalidTokenException invalidTokenException) {
        return new ErrorTemplate(invalidTokenException.getMessage());
    }
}
