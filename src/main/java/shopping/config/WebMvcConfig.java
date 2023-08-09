package shopping.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.auth.interceptor.LoginTokenInterceptor;
import shopping.auth.resolver.AuthMemberResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginTokenInterceptor loginTokenInterceptor;

    public WebMvcConfig(LoginTokenInterceptor loginTokenInterceptor) {
        this.loginTokenInterceptor = loginTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTokenInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/error", "/css/**", "/assets/**", "/login", "/js/**",
                "/*.ico", "/view/**", "/products/**", "/order/**", "/order-history");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthMemberResolver());
    }


}
