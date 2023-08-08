package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.service.LoginService;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String renderLoginPage() {
        return "/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest loginRequest) {
        final LoginResponse loginResponse = loginService.login(loginRequest);

        return ResponseEntity.ok(loginResponse);
    }
}
