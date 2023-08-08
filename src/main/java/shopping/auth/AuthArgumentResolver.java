package shopping.auth;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shopping.exception.AuthExceptionType;
import shopping.exception.ShoppingException;

@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtHelper jwtHelper;
    private final TokenExtractor tokenExtractor;

    public AuthArgumentResolver(final JwtHelper jwtHelper, final TokenExtractor tokenExtractor) {
        this.jwtHelper = jwtHelper;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
        throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String jwt = tokenExtractor.extract(request);
        if (!jwtHelper.validateToken(jwt)) {
            throw new ShoppingException(AuthExceptionType.INVALID_TOKEN, jwt);
        }

        return Long.valueOf(jwtHelper.getSubject(jwt));
    }
}
