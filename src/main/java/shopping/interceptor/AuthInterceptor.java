package shopping.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.ShoppingException;
import shopping.infrastructure.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    public AuthInterceptor(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String accessToken = request.getHeader("Authorization");
        System.out.println("accessToken = " + accessToken);
        validateNotNull(accessToken);

        final String token = extractToken(accessToken);
        validateToken(token);

        request.setAttribute("userId", jwtProvider.getPayload(token));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void validateToken(String token) {
        if (!jwtProvider.validateToken(token)) {
            throw new ShoppingException("토큰이 유효하지 않습니다.");
        }
    }

    private void validateNotNull(String accessToken) {
        if (accessToken == null) {
            throw new ShoppingException("토큰 정보가 없습니다.");
        }
    }

    private String extractToken(String accessToken) {
        final String tokenType = accessToken.split(" ")[0];

        if (!tokenType.equals("Bearer")) {
            throw new ShoppingException("토큰이 유효하지 않습니다.");
        }

        final String token = accessToken.split(" ")[1];
        return token;
    }
}
