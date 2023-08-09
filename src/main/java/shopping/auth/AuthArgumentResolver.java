package shopping.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shopping.exception.auth.UserNotFoundException;
import shopping.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenExtractor tokenExtractor;
    private final UserRepository userRepository;

    public AuthArgumentResolver(TokenExtractor tokenExtractor, UserRepository userRepository) {
        this.tokenExtractor = tokenExtractor;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class) &&
                parameter.hasParameterAnnotation(UserIdPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Long userId = tokenExtractor.extractUserId(request);
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException();
        }
        return userId;
    }
}
