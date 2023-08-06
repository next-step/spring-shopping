package shopping.auth.service.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shopping.auth.service.infra.JwtUtils;
import shopping.auth.service.interceptor.AuthInterceptor;
import shopping.auth.service.interceptor.TokenPerRequest;

@Configuration
class InterceptorConfigurer implements WebMvcConfigurer {

    private final JwtUtils jwtUtils;
    private final List<AuthorizationPath> authorizationPaths;
    private final TokenPerRequest tokenPerRequest;

    InterceptorConfigurer(JwtUtils jwtUtils, List<AuthorizationPath> authorizationPaths,
        TokenPerRequest tokenPerRequest) {
        this.jwtUtils = jwtUtils;
        this.authorizationPaths = authorizationPaths;
        this.tokenPerRequest = tokenPerRequest;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthInterceptor authInterceptor = new AuthInterceptor(jwtUtils, tokenPerRequest);
        registry.addInterceptor(authInterceptor)
            .addPathPatterns(this.authorizationPaths.stream()
                .map(AuthorizationPath::path)
                .toArray(String[]::new));
    }
}
