package shopping.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.AuthException;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    private final TokenProvider tokenProvider;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AuthInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String accessToken = request.getHeader(AUTHORIZATION);
        if (accessToken == null) {
            log.error("액세스 토큰이 존재하지 않습니다.");
            throw new AuthException();
        }
        tokenProvider.getEmail(accessToken);
        return true;
    }
}
