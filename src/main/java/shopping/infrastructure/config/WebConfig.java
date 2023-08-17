package shopping.infrastructure.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import shopping.infrastructure.PasswordEncoder;

import java.time.Duration;

@Configuration
public class WebConfig {

    @Bean
    public PasswordEncoder passwordEncoder(final PasswordEncoder passwordEncoder) {
        return passwordEncoder;
    }

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(3))
                .build();
    }
}
