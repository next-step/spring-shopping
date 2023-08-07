package shopping.ui.argumentresolver;

import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import shopping.exception.AuthException;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String LOGIN_ATTRIBUTE_KEY = "memberId";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        validateRequest(request);
        return request.getAttribute(LOGIN_ATTRIBUTE_KEY);
    }

    private static void validateRequest(HttpServletRequest request) {
        if (Objects.isNull(request.getAttribute(LOGIN_ATTRIBUTE_KEY))) {
            throw new AuthException("로그인이 필요한 서비스입니다");
        }
    }
}
