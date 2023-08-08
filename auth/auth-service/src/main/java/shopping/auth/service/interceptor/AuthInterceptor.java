package shopping.auth.service.interceptor;

import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.auth.app.exception.InvalidTokenException;
import shopping.auth.service.infra.JwtUtils;

public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final TokenPerRequest tokenPerRequest;

    public AuthInterceptor(JwtUtils jwtUtils, TokenPerRequest tokenPerRequest) {
        this.jwtUtils = jwtUtils;
        this.tokenPerRequest = tokenPerRequest;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            String id = jwtUtils.payload(token);
            tokenPerRequest.setDecryptedToken(id);
        } catch (Exception exception) {
            throw new InvalidTokenException(MessageFormat.format("인증할 수 없는 토큰 \"{0}\" 입니다.", token));
        }
        return true;
    }

}
