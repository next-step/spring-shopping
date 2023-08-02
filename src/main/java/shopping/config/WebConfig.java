package shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.controller.TokenInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    public WebConfig(final TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/cart-items");
    }
}
