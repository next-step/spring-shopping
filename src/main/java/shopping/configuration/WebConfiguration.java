package shopping.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.auth.AuthArgumentResolver;
import shopping.auth.AuthInterceptor;
import shopping.auth.TokenExtractor;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    private final TokenExtractor tokenExtractor;

    public WebConfiguration(TokenExtractor tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(tokenExtractor))
                .addPathPatterns("/cart/items/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(tokenExtractor));
    }
}
