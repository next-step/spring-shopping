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

    private final JwtResolver jwtResolver;
    private final TokenExtractor tokenExtractor;

    public AuthArgumentResolver(
        final JwtResolver jwtResolver,
        final TokenExtractor tokenExtractor
    ) {
        this.jwtResolver = jwtResolver;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String jwt = tokenExtractor.extract(request);
        if (!jwtResolver.validateToken(jwt)) {
            throw new ShoppingException(AuthExceptionType.INVALID_TOKEN, jwt);
        }

        return Long.valueOf(jwtResolver.getSubject(jwt));
    }
}
