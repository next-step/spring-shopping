package shopping.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import shopping.jwt.TokenManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCheckInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenManager tokenManager;

    public LoginCheckInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            Long memberId = tokenManager.decodeToken(authorization.substring(BEARER_PREFIX.length()));
            request.setAttribute("memberId", memberId);
            return true;
        }

        return false;
    }
}
