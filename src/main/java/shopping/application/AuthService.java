package shopping.application;

import org.springframework.stereotype.Service;
import shopping.domain.Email;
import shopping.domain.Member;
import shopping.domain.TokenProvider;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.AuthException;
import shopping.repository.MemberRepository;


@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public AuthService(MemberRepository memberRepository, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Email email = new Email(loginRequest.getEmail());
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("존재하지 않는 사용자입니다."));

        if (!member.matchPassword(loginRequest.getPassword())) {
            throw new AuthException("비밀번호가 일치하지 않습니다.");
        }

        String token = tokenProvider.createToken(member.getId());
        return new LoginResponse(token);
    }
}
