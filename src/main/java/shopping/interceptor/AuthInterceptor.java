package shopping.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.auth.TokenProvider;
import shopping.exception.AuthException;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    private final TokenProvider tokenProvider;

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
        tokenProvider.getEmail(accessToken);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}