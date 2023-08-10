package shopping.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.application.AuthService;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }
}
