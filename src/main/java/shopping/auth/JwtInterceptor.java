package shopping.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.exception.AuthExceptionType;
import shopping.exception.ShoppingException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtResolver jwtResolver;
    private final TokenExtractor tokenExtractor;

    public JwtInterceptor(final JwtResolver jwtResolver, final BearerExtractor tokenExtractor) {
        this.jwtResolver = jwtResolver;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) {
        final String jwt = tokenExtractor.extract(request);
        if (!jwtResolver.validateToken(jwt)) {
            throw new ShoppingException(AuthExceptionType.INVALID_TOKEN, jwt);
        }

        return true;
    }
}
