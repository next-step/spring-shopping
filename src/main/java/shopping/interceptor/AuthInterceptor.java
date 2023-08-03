package shopping.interceptor;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;
import shopping.utils.JwtProvider;

public class AuthInterceptor implements HandlerInterceptor {

    public static final String BEARER_TOKEN_TYPE = "Bearer ";
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
            throw new ShoppingException(ErrorCode.NO_AUTHENTICATION_HEADER);
        }

        if (!header.startsWith(BEARER_TOKEN_TYPE)) {
            throw new ShoppingException(ErrorCode.INVALID_TOKEN_TYPE);
        }

        String token = header.substring(BEARER_TOKEN_TYPE.length());
        if (!jwtProvider.validate(token)) {
            throw new ShoppingException(ErrorCode.INVALID_TOKEN);
        }

        Long userId = Long.valueOf(jwtProvider.parseToken(token));
        request.setAttribute("userId", userId);
        return true;
    }
}
