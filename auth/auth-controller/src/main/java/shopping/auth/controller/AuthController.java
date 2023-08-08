package shopping.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.app.api.AuthUseCase;
import shopping.auth.app.api.request.LoginRequest;
import shopping.auth.app.api.response.TokenResponse;
import shopping.auth.app.exception.AlreadyExistUserException;
import shopping.auth.app.exception.DoesNotExistUserException;
import shopping.auth.app.exception.InvalidEmailException;
import shopping.auth.app.exception.InvalidPasswordException;
import shopping.core.util.ErrorTemplate;

@RestController
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login/token")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest request) {
        return authUseCase.authenticate(request);
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
