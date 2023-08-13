package shopping.auth;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import shopping.exception.auth.AuthException;

@Component
public class TokenExtractor {

    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    private final TokenProvider tokenProvider;

    public TokenExtractor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public void validateToken(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION);
        validateHeaderExist(accessToken);
        if (!tokenProvider.isSignedToken(accessToken)) {
            throw new AuthException();
        }
    }

    public Long extractUserId(HttpServletRequest request) {
        String accessToken = request.getHeader(AUTHORIZATION);
        validateHeaderExist(accessToken);
        return tokenProvider.getId(accessToken.substring(BEARER.length()));
    }

    private void validateHeaderExist(String accessToken) {
        if (accessToken == null) {
            throw new AuthException();
        }
    }
}
