package shopping.member.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.TokenProvider;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.member.dto.LoginRequest;
import shopping.member.dto.LoginResponse;
import shopping.member.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public MemberService(MemberRepository memberRepository, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    @Transactional(readOnly = true)
    public LoginResponse loginMember(LoginRequest loginRequest) {
        Member member = getMemberByEmail(loginRequest.getEmail());

        if (!member.login(loginRequest.getPassword())) {
            throw new WooWaException("로그인 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        return new LoginResponse(tokenProvider.createToken(member.getId().toString()));
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new WooWaException("로그인 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST));
    }
}
