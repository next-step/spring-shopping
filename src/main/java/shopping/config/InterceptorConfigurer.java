package shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.infra.JwtUtils;
import shopping.interceptor.AuthInterceptor;

@Configuration
class InterceptorConfigurer implements WebMvcConfigurer {

    private final JwtUtils jwtUtils;

    InterceptorConfigurer(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthInterceptor authInterceptor = new AuthInterceptor(jwtUtils);
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/carts");
    }
}
