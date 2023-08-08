package shopping.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.AuthExceptionType;
import shopping.exception.ShoppingException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtHelper jwtHelper;
    private final TokenExtractor tokenExtractor;

    public JwtInterceptor(final JwtHelper jwtHelper, final BearerExtractor tokenExtractor) {
        this.jwtHelper = jwtHelper;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {
        final String jwt = tokenExtractor.extract(request);
        if (!jwtHelper.validateToken(jwt)) {
            throw new ShoppingException(AuthExceptionType.INVALID_TOKEN, jwt);
        }

        return true;
    }
}
