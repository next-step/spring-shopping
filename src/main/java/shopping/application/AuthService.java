package shopping.application;

import org.springframework.stereotype.Service;
import shopping.domain.Member;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.exception.LoginException;
import shopping.jwt.TokenManager;
import shopping.repository.MemberRepository;


@Service
public class AuthService {

    private MemberRepository memberRepository;
    private TokenManager tokenProvider;

    public AuthService(MemberRepository memberRepository, TokenManager tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new LoginException("존재하지 않는 사용자입니다."));

        if (!member.matchPassword(loginRequest.getPassword())) {
            throw new LoginException("비밀번호가 일치하지 않습니다.");
        }

        String token = tokenProvider.createToken(member.getId());
        return new LoginResponse(token);
    }
}
