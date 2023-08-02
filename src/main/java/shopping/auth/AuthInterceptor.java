package shopping.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.AuthException;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    private final TokenProvider tokenProvider;

    public AuthInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader(AUTHORIZATION);
        validateHeaderExist(accessToken);
        String email = tokenProvider.getEmail(accessToken.substring(BEARER.length()));
        request.setAttribute("email", email);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void validateHeaderExist(String accessToken) {
        if (accessToken == null) {
            throw new AuthException();
        }
    }
}
