package shopping.ui.config;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String TOKEN_DELIMITER = " ";

    private final TokenConsumer tokenConsumer;

    public AuthInterceptor(final TokenConsumer tokenConsumer) {
        this.tokenConsumer = tokenConsumer;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateNotNull(accessToken);
        validateTokenType(accessToken);

        final String token = extractToken(accessToken);
        validateExtractedToken(token);

        request.setAttribute("userId", tokenConsumer.getPayload(token));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private String extractToken(final String accessToken) {
        return accessToken.split(TOKEN_DELIMITER)[1];
    }

    private void validateExtractedToken(final String token) {
        if (!tokenConsumer.validateToken(token)) {
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
