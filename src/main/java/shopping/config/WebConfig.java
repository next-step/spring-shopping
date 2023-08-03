package shopping.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.auth.AuthArgumentResolver;
import shopping.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    public WebConfig(final JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/login");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver());
    }
}
