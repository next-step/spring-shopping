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
    private final BearerExtractor bearerExtractor;

    public JwtInterceptor(final JwtHelper jwtHelper, final BearerExtractor bearerExtractor) {
        this.jwtHelper = jwtHelper;
        this.bearerExtractor = bearerExtractor;
    }

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {
        final String jwt = bearerExtractor.extract(request);
        if (!jwtHelper.validateToken(jwt)) {
            throw new ShoppingException(AuthExceptionType.INVALID_TOKEN, jwt);
        }

        request.setAttribute("memberId", Long.parseLong(jwtHelper.getSubject(jwt)));
        return true;
    }
}
