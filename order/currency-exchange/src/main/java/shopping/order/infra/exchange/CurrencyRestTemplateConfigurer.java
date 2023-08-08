package shopping.order.infra.exchange;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CurrencyRestTemplateConfigurer {

    private static final int MAXIMUM_WAIT_SECONDS = 10;

    @Bean
    public RestTemplate currencyRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(MAXIMUM_WAIT_SECONDS))
                .setReadTimeout(Duration.ofSeconds(MAXIMUM_WAIT_SECONDS))
                .build();
    }
}
