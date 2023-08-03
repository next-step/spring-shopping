package shopping.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";

    private final TokenProvider tokenProvider;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AuthArgumentResolver(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(String.class) &&
                parameter.hasParameterAnnotation(EmailFromAccessToken.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String accessToken = webRequest.getHeader(AUTHORIZATION);
        String email = tokenProvider.getEmail(accessToken);
        log.debug("접근한 유저의 이메일 : {}", email);
        return email;
    }
}
