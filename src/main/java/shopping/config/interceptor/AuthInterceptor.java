package shopping.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;
import shopping.infrastructure.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String AUTHENTICATION_HEADER = "Authorization";

    private final JwtProvider jwtProvider;

    public AuthInterceptor(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String accessToken = request.getHeader(AUTHENTICATION_HEADER);
        validateNotNull(accessToken);

        final String token = extractToken(accessToken);
        validateToken(token);

        request.setAttribute("userId", jwtProvider.getPayload(token));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void validateToken(String token) {
        if (!jwtProvider.validateToken(token)) {
            throw new ShoppingException(ErrorType.TOKEN_INVALID);
        }
    }

    private void validateNotNull(String accessToken) {
        if (accessToken == null) {
            throw new ShoppingException(ErrorType.NO_TOKEN);
        }
    }

    private String extractToken(String accessToken) {
        final String tokenType = accessToken.split(" ")[0];

        if (!tokenType.equals(TOKEN_TYPE)) {
            throw new ShoppingException(ErrorType.TOKEN_INVALID);
        }

        final String token = accessToken.split(" ")[1];
        return token;
    }
}
