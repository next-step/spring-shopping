package shopping.auth.controlller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.auth.dto.request.LoginRequest;
import shopping.auth.dto.response.LoginResponse;
import shopping.auth.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login/token")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
