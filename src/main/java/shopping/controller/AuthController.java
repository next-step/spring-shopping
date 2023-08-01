package shopping.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.dto.LoginRequest;
import shopping.dto.TokenResponse;
import shopping.service.AuthService;

@RestController
public class AuthController {

    private final AuthService authService;

    AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest request) {
        return authService.authenticate(request);
    }

}
