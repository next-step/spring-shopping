package shopping.auth;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import shopping.exception.ShoppingAuthenticationException;

@Component
public class BearerExtractor {

    private static final String BEARER_TYPE = "Bearer";

    public String extract(final HttpServletRequest request) {
        final String value = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (value == null) {
            throw new ShoppingAuthenticationException("토큰 헤더가 존재하지 않습니다.");
        }
        if (!StringUtils.hasText(value)) {
            throw new ShoppingAuthenticationException("토큰 값이 존재하지 않습니다.");
        }
        if (!value.startsWith(BEARER_TYPE)) {
            throw new ShoppingAuthenticationException("토큰이 Bearer로 시작하지 않습니다.");
        }

        return value.split(" ")[1];
    }
}