package shopping.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.auth.TokenProvider;
import shopping.auth.domain.LoggedInMember;
import shopping.exception.WooWaException;
import shopping.member.repository.MemberRepository;

@Component
public class LoginTokenInterceptor implements HandlerInterceptor {

    public static final String MEMBER_KEY = "memberId";
    private static final String BEARER_TYPE = "Bearer";
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public LoginTokenInterceptor(TokenProvider tokenProvider, MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        String value = request.getHeader("Authorization");

        if (value == null) {
            throw new WooWaException("권한이 없습니다", HttpStatus.UNAUTHORIZED);
        }

        String accessToken = value.substring(BEARER_TYPE.length()).trim();

        if (!tokenProvider.validateToken(accessToken)) {
            throw new WooWaException("권한이 없습니다", HttpStatus.UNAUTHORIZED);
        }

        LoggedInMember loggedInMember = new LoggedInMember(tokenProvider.getPayload(accessToken));
        validateMember(loggedInMember.getId());
        request.setAttribute(MEMBER_KEY, loggedInMember);
        return true;
    }

    private void validateMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new WooWaException("등록되지 않은 유저입니다. memberId: " + memberId, HttpStatus.BAD_REQUEST);
        }
    }
}
