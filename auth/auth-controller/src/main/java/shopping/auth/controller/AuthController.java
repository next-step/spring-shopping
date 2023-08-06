package shopping.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.domain.exception.AlreadyExistUserException;
import shopping.auth.domain.exception.DoesNotExistUserException;
import shopping.auth.domain.exception.InvalidEmailException;
import shopping.auth.domain.exception.InvalidPasswordException;
import shopping.auth.service.AuthService;
import shopping.auth.service.dto.LoginRequest;
import shopping.auth.service.dto.TokenResponse;
import shopping.core.util.ErrorTemplate;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest request) {
        return authService.authenticate(request);
    }

    @ExceptionHandler({AlreadyExistUserException.class,
        DoesNotExistUserException.class,
        InvalidEmailException.class,
        InvalidPasswordException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorTemplate handleBadRequest(RuntimeException runtimeException) {
        return new ErrorTemplate(runtimeException.getMessage());
    }
}
