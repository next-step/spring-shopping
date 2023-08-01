package shopping.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import shopping.exception.WooWaException;
import shopping.member.dto.LoginRequest;
import shopping.member.dto.LoginResponse;
import shopping.member.service.MemberService;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping ("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        validateNonNull(loginRequest);
        return memberService.loginMember(loginRequest);
    }

    private static void validateNonNull(LoginRequest loginRequest) {
        if (!StringUtils.hasText(loginRequest.getEmail()) || !StringUtils.hasText(loginRequest.getPassword())) {
            throw new WooWaException("로그인 입력값이 있어야합니다.", new IllegalArgumentException(), HttpStatus.BAD_REQUEST);
        }
    }
}
