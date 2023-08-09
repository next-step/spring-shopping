package shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.config.argumentresolver.AuthenticationPrincipalArgumentResolver;
import shopping.config.interceptor.AuthInterceptor;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AuthenticationPrincipalArgumentResolver resolver;

    public WebMvcConfiguration(final AuthInterceptor authInterceptor,
                               final AuthenticationPrincipalArgumentResolver resolver) {
        this.authInterceptor = authInterceptor;
        this.resolver = resolver;
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/cart").setViewName("cart");
        registry.addViewController("/order-history").setViewName("order-history");
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/carts/**")
                .addPathPatterns("/orders/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}