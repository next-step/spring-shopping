package shopping.argumentresolver;

import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shopping.argumentresolver.annotation.UserId;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String USER_ID = "userId";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) && Objects.equals(
            parameter.getParameterType(), Long.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return Optional.ofNullable((Long) request.getAttribute(USER_ID))
            .orElseThrow(() -> new ShoppingException(ErrorCode.NO_AUTHORIZATION_HEADER));
    }
}
