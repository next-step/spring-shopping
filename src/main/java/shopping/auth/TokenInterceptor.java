package shopping.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.AuthException;
import shopping.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String BEARER_HEADER_NAME = "Bearer ";
    private static final String LOGIN_MEMBER_ID = "loginMemberId";

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
            throw new AuthException(ErrorCode.TOKEN_INVALID);
        }
        request.setAttribute(LOGIN_MEMBER_ID, jwtTokenProvider.getPayload(token));
        return true;
    }

    private String getToken(final HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(bearerToken)) {
            throw new AuthException(ErrorCode.TOKEN_IS_EMPTY);
        }
        return StringUtils.delete(bearerToken, BEARER_HEADER_NAME);
    }
}
