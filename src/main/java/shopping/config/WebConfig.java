package shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.interceptor.LoginCheckInterceptor;
import shopping.jwt.TokenManager;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TokenManager tokenManager;

    public WebConfig(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(tokenManager))
                .addPathPatterns("/cart/products/**");
    }
}
