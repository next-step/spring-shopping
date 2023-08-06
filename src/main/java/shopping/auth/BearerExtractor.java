package shopping.auth;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import shopping.exception.ExceptionType;
import shopping.exception.ShoppingException;

@Component
public class BearerExtractor {

    private static final String BEARER_TYPE = "Bearer";
    private static final int TOKEN_VALUE_BEGIN_INDEX = 7;

    public String extract(final HttpServletRequest request) {
        final String value = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (value == null) {
            throw new ShoppingException(ExceptionType.NO_AUTHORIZATION_HEADER);
        }
        if (!StringUtils.hasText(value)) {
            throw new ShoppingException(ExceptionType.NO_CONTENT_TOKEN);
        }
        if (!value.startsWith(BEARER_TYPE)) {
            throw new ShoppingException(ExceptionType.NOT_BEARER_TOKEN);
        }

        return value.substring(TOKEN_VALUE_BEGIN_INDEX);
    }
}
