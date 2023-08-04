package shopping.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    private final TokenExtractor tokenExtractor;

    public AuthInterceptor(TokenExtractor tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        tokenExtractor.extractToken(request);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
