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
            throw new AuthException();
        }
        String email = tokenProvider.getEmail(accessToken.substring(7));
        request.setAttribute("email", email);
        log.debug(email);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
