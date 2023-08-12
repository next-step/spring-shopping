package shopping.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final long CONNECTION_TIMEOUT_SEC = 1;
    private static final long READ_TIMEOUT_SEC = 1;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
            .setConnectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT_SEC))
            .setReadTimeout(Duration.ofSeconds(READ_TIMEOUT_SEC))
            .build();
    }
}
