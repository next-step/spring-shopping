package shopping.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.auth.AuthArgumentResolver;
import shopping.auth.JwtInterceptor;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final AuthArgumentResolver authArgumentResolver;

    public AuthConfig(final JwtInterceptor jwtInterceptor,
        final AuthArgumentResolver authArgumentResolver) {
        this.jwtInterceptor = jwtInterceptor;
        this.authArgumentResolver = authArgumentResolver;
    }


    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
            .excludePathPatterns("/", "/login", "/cart", "/order-history", "/order-detail/**")
            .excludePathPatterns("/api/login")
            .excludePathPatterns("/css/**", "/js/**", "/assets/**", "/favicon.ico");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
