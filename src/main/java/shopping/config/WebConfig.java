package shopping.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.domain.SecurityInfoManager;
import shopping.repository.MemberRepository;
import shopping.ui.argumentresolver.LoginArgumentResolver;
import shopping.ui.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SecurityInfoManager securityInfoManager;
    private final MemberRepository memberRepository;

    public WebConfig(SecurityInfoManager securityInfoManager, MemberRepository memberRepository) {
        this.securityInfoManager = securityInfoManager;
        this.memberRepository = memberRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(memberRepository, securityInfoManager))
            .addPathPatterns("/api/cart/**")
            .addPathPatterns("/api/order/**")
            .addPathPatterns("/api/order-detail/**")
            .addPathPatterns("/api/order-history/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }
}
