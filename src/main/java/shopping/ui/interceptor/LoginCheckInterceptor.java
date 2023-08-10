package shopping.ui.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.AuthException;
import shopping.infrastructure.TokenManager;
import shopping.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

public class LoginCheckInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final MemberRepository memberRepository;
    private final TokenManager tokenManager;

    public LoginCheckInterceptor(MemberRepository memberRepository, TokenManager tokenManager) {
        this.memberRepository = memberRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            Long memberId = tokenManager.decodeToken(authorization.substring(BEARER_PREFIX.length()));

            memberRepository.findById(memberId)
                    .orElseThrow(() -> new AuthException(
                            MessageFormat.format("memberId에 해당하는 회원이 존재하지 않습니다 id : {0}", memberId)
                    ));

            request.setAttribute("memberId", memberId);
            return true;
        }

        throw new AuthException("로그인이 필요한 서비스입니다");
    }
}
