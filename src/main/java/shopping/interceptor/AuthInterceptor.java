package shopping.interceptor;

import java.util.Objects;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.utils.JwtProvider;

public class AuthInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private final JwtProvider jwtProvider;

    public AuthInterceptor(final JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
        final Object handler) throws Exception {
        logger.info("[AuthInterceptor] url : {}", request.getRequestURI());

        final String header = request.getHeader("Authorization");
        if (Objects.isNull(header)) {
            throw new AuthenticationException("등록된 사용자가 아닙니다");
        }

        if (!header.startsWith("Bearer")) {
            throw new AuthenticationException("지원하는 토큰 타입이 아닙니다");
        }

        String token = header.substring(7);
        if (!jwtProvider.validate(token)) {
            throw new AuthenticationException("유효하지 않은 토큰 입니다");
        }

        Long userId = Long.valueOf(jwtProvider.parseToken(token));
        request.setAttribute("userId", userId);
        return true;
    }
}
