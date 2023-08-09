package shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.jwt.TokenManager;
import shopping.repository.MemberRepository;
import shopping.ui.argumentresolver.LoginArgumentResolver;
import shopping.ui.interceptor.LoginCheckInterceptor;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TokenManager tokenManager;
    private final MemberRepository memberRepository;

    public WebConfig(TokenManager tokenManager, MemberRepository memberRepository) {
        this.tokenManager = tokenManager;
        this.memberRepository = memberRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(memberRepository, tokenManager))
                .addPathPatterns("/cart/products/**")
            .addPathPatterns("/order/**")
            .addPathPatterns("/order-history/member/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }
}
