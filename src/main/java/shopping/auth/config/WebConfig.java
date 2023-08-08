package shopping.auth.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final AuthArgumentResolver authArgumentResolver;

    public WebConfig(final JwtInterceptor jwtInterceptor,
        final AuthArgumentResolver authArgumentResolver) {
        this.jwtInterceptor = jwtInterceptor;
        this.authArgumentResolver = authArgumentResolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/cart", "/login", "/order-detail/**")
            .excludePathPatterns("/api/login")
            .excludePathPatterns("/error", "/css/**", "/assets/**", "/js/**", "/*.ico");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
