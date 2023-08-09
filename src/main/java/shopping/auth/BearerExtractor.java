package shopping.auth;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import shopping.exception.AuthExceptionType;
import shopping.exception.ShoppingException;

@Component
public class BearerExtractor implements TokenExtractor {

    private static final String BEARER_TYPE = "Bearer";
    private static final int TOKEN_VALUE_BEGIN_INDEX = 7;

    public String extract(final HttpServletRequest request) {
        final String value = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (value == null) {
            throw new ShoppingException(AuthExceptionType.NO_AUTHORIZATION_HEADER);
        }
        if (!StringUtils.hasText(value)) {
            throw new ShoppingException(AuthExceptionType.NO_CONTENT_TOKEN);
        }
        if (!value.startsWith(BEARER_TYPE)) {
            throw new ShoppingException(AuthExceptionType.NOT_BEARER_TOKEN);
        }

        return value.substring(TOKEN_VALUE_BEGIN_INDEX);
    }
}
