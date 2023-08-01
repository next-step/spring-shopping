package shopping.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.dto.LoginRequest;
import shopping.dto.TokenResponse;
import shopping.service.AuthService;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest request) {
        return authService.authenticate(request);
    }

}
