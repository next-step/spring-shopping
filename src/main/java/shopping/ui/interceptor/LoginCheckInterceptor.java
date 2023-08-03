package shopping.ui.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.AuthException;
import shopping.jwt.TokenManager;
import shopping.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCheckInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    public LoginCheckInterceptor(MemberRepository memberRepository, TokenManager tokenManager) {
        this.memberRepository = memberRepository;
        this.tokenManager = tokenManager;
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            Long memberId = tokenManager.decodeToken(authorization.substring(BEARER_PREFIX.length()));

            memberRepository.findById(memberId)
                    .orElseThrow(() -> new AuthException("memberId에 해당하는 회원이 존재하지 않습니다"));

            request.setAttribute("memberId", memberId);
            return true;
        }

        return false;
    }
}
