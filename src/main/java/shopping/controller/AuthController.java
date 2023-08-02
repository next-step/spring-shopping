package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.domain.member.Member;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.service.AuthService;
import shopping.service.MemberService;

@Controller
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    public AuthController(final AuthService authService, final MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> postLogin(@RequestBody LoginRequest loginRequest) {
        final Member member = memberService.matchMember(loginRequest);

        return ResponseEntity.ok(new LoginResponse(authService.createToken(member.getId())));
    }
}
