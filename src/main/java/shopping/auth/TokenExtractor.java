package shopping.auth;

import org.springframework.stereotype.Component;
import shopping.exception.AuthException;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenExtractor {

    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    private final TokenProvider tokenProvider;

    public TokenExtractor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public String extractToken(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION);
        validateHeaderExist(accessToken);
        return tokenProvider.getEmail(accessToken.substring(BEARER.length()));
    }

    private void validateHeaderExist(String accessToken) {
        if (accessToken == null) {
            throw new AuthException();
        }
    }
}
