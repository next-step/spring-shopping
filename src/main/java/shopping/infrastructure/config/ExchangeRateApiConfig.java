package shopping.infrastructure.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("!test")
public class ExchangeRateApiConfig {

    private static final int CONNECTION_TIME_OUT = 5000;
    private static final int READ_TIME_OUT = 5000;

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder
            .requestFactory(
                () -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
            .setConnectTimeout(Duration.ofMillis(CONNECTION_TIME_OUT))
            .setReadTimeout(Duration.ofMillis(READ_TIME_OUT))
            .build();
    }
}

