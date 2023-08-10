package shopping.application;

import org.springframework.stereotype.Service;
import shopping.domain.Member;
import shopping.dto.LoginRequest;
import shopping.dto.LoginResponse;
import shopping.exception.AuthException;
import shopping.infrastructure.TokenManager;
import shopping.repository.MemberRepository;

import java.text.MessageFormat;


@Service
public class AuthService {

    private MemberRepository memberRepository;
    private TokenManager tokenProvider;

    public AuthService(MemberRepository memberRepository, TokenManager tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = getMember(loginRequest);

        validatePassword(loginRequest, member);

        String token = tokenProvider.createToken(member.getId());
        return new LoginResponse(token);
    }

    private void validatePassword(LoginRequest loginRequest, Member member) {
        if (member.mismatchPassword(loginRequest.getPassword())) {
            throw new AuthException("비밀번호가 일치하지 않습니다");
        }
    }

    private Member getMember(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AuthException(
                        MessageFormat.format("존재하지 않는 사용자입니다 email : {0})", loginRequest.getEmail())
                ));
        return member;
    }
}
