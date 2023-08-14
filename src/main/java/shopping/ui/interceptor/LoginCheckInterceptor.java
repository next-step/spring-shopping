package shopping.ui.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import shopping.domain.SecurityInfo;
import shopping.domain.SecurityInfoManager;
import shopping.exception.AuthException;
import shopping.repository.MemberRepository;

public class LoginCheckInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    public LoginCheckInterceptor(MemberRepository memberRepository, SecurityInfoManager securityInfoManager) {
        this.memberRepository = memberRepository;
        this.securityInfoManager = securityInfoManager;
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final SecurityInfoManager securityInfoManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            SecurityInfo securityInfo = securityInfoManager.decode(authorization.substring(BEARER_PREFIX.length()));
            Long memberId = (Long) securityInfo.getPrincipal();

            memberRepository.findById(memberId)
                    .orElseThrow(() -> new AuthException("memberId에 해당하는 회원이 존재하지 않습니다"));

            request.setAttribute("memberId", memberId);
            return true;
        }

        throw new AuthException("로그인이 필요한 서비스입니다");
    }
}
