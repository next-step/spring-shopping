package shopping.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.auth.TokenProvider;
import shopping.auth.domain.LoggedInMember;
import shopping.exception.WooWaException;

@Component
public class LoginTokenInterceptor implements HandlerInterceptor {

    public static final String MEMBER_KEY = "memberId";
    private static final String BEARER_TYPE = "Bearer";
    private final TokenProvider tokenProvider;

    public LoginTokenInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        String value = request.getHeader("Authorization");

        if (value == null) {
            throw new WooWaException("권한이 없습니다", HttpStatus.UNAUTHORIZED);
        }

        String accessToken = value.substring(BEARER_TYPE.length()).trim();

        if (!tokenProvider.validateToken(accessToken)) {
            throw new WooWaException("권한이 없습니다", HttpStatus.UNAUTHORIZED);
        }

        request.setAttribute(MEMBER_KEY, new LoggedInMember(tokenProvider.getPayload(accessToken)));
        return true;
    }
}
