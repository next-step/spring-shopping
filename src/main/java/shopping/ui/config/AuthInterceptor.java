package shopping.ui.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;
import shopping.infrastructure.TokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String AUTHENTICATION_HEADER = "Authorization";
    private static final String TOKEN_DELIMITER = " ";

    private final TokenProvider tokenProvider;

    public AuthInterceptor(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final String accessToken = request.getHeader(AUTHENTICATION_HEADER);
        validateNotNull(accessToken);
        validateTokenType(accessToken);

        final String token = extractToken(accessToken);
        validateExtractedToken(token);

        request.setAttribute("userId", tokenProvider.getPayload(token));

        return true;
    }

    private String extractToken(final String accessToken) {
        return accessToken.split(TOKEN_DELIMITER)[1];
    }

    private void validateExtractedToken(final String token) {
        if (!tokenProvider.validateToken(token)) {
            throw new ShoppingException(ErrorType.TOKEN_INVALID);
        }
    }

    private void validateNotNull(final String accessToken) {
        if (accessToken == null) {
            throw new ShoppingException(ErrorType.NO_TOKEN);
        }
    }

    private void validateTokenType(final String accessToken) {
        final String tokenType = accessToken.split(TOKEN_DELIMITER)[0];
        if (!tokenType.equals(TOKEN_TYPE)) {
            throw new ShoppingException(ErrorType.TOKEN_INVALID);
        }
    }
}
