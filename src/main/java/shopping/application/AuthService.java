package shopping.application;

import org.springframework.stereotype.Service;
import shopping.domain.Email;
import shopping.domain.Member;
import shopping.domain.SecurityInfo;
import shopping.domain.SecurityInfoManager;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.AuthException;
import shopping.repository.MemberRepository;


@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final SecurityInfoManager securityInfoManager;

    public AuthService(MemberRepository memberRepository, SecurityInfoManager securityInfoManager) {
        this.memberRepository = memberRepository;
        this.securityInfoManager = securityInfoManager;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Email email = new Email(loginRequest.getEmail());
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("존재하지 않는 사용자입니다."));

        if (!member.matchPassword(loginRequest.getPassword())) {
            throw new AuthException("비밀번호가 일치하지 않습니다.");
        }

        String token = securityInfoManager.encode(new SecurityInfo(member.getId()));
        return new LoginResponse(token);
    }
}
