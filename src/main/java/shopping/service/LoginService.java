package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.member.Email;
import shopping.domain.member.Member;
import shopping.domain.member.Password;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.LoginResponse;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingApiException;
import shopping.repository.MemberRepository;
import shopping.security.JwtTokenProvider;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginService(final MemberRepository memberRepository, final JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(final LoginRequest loginRequest) {
        final Member member = memberRepository.findByEmail(Email.from(loginRequest.getEmail()))
                .orElseThrow(() -> new ShoppingApiException(ErrorCode.NOT_FOUND_MEMBER_EMAIL));

        final Password requestPassword = Password.from(loginRequest.getPassword());
        member.matchPassword(requestPassword);

        final String token = jwtTokenProvider.createToken(member.getId());
        return new LoginResponse(token);
    }
}
