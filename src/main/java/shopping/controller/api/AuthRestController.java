package shopping.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.domain.member.Member;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.service.AuthService;
import shopping.service.MemberService;

@RestController
@RequestMapping("/api")
public class AuthRestController {

    private final AuthService authService;
    private final MemberService memberService;

    public AuthRestController(final AuthService authService, final MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> postLogin(@RequestBody LoginRequest loginRequest) {
        final Member member = memberService.matchMember(loginRequest);

        return ResponseEntity.ok(new LoginResponse(authService.createToken(member.getId())));
    }
}
