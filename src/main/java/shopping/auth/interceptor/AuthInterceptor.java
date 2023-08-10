package shopping.auth.interceptor;

import java.text.MessageFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.auth.infra.JwtUtils;
import shopping.core.exception.BadRequestException;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String INVALID_TOKEN = "AUTH-INTERCEPTOR-401";

    private final JwtUtils jwtUtils;

    public AuthInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            String id = jwtUtils.payload(token);
            request.setAttribute("userId", Long.valueOf(id));
        } catch (Exception exception) {
            throw new BadRequestException(MessageFormat.format("인증할 수 없는 토큰 \"{0}\" 입니다.", token), INVALID_TOKEN);
        }
        return true;
    }
}
