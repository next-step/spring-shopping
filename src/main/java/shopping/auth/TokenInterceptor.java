package shopping.auth;

import static shopping.exception.ShoppingErrorType.TOKEN_INVALID;
import static shopping.exception.ShoppingErrorType.TOKEN_IS_EMPTY;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.ShoppingException;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String BEARER_HEADER_NAME = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    public TokenInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler
    ) {
        final String token = getToken(request);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new ShoppingException(TOKEN_INVALID);
        }
        request.setAttribute("loginMemberId", jwtTokenProvider.getSubject(token));
        return true;
    }

    private String getToken(final HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(bearerToken)) {
            throw new ShoppingException(TOKEN_IS_EMPTY);
        }
        return StringUtils.delete(bearerToken, BEARER_HEADER_NAME);
    }
}
