package shopping.ui.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.domain.TokenProvider;
import shopping.exception.AuthException;
import shopping.repository.MemberRepository;

public class LoginCheckInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    public LoginCheckInterceptor(MemberRepository memberRepository, TokenProvider<Long> tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider<Long> tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            Long memberId = tokenProvider.decodeToken(authorization.substring(BEARER_PREFIX.length()));

            memberRepository.findById(memberId)
                    .orElseThrow(() -> new AuthException("memberId에 해당하는 회원이 존재하지 않습니다"));

            request.setAttribute("memberId", memberId);
            return true;
        }

        throw new AuthException("로그인이 필요한 서비스입니다");
    }
}
