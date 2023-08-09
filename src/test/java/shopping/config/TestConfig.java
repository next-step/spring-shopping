package shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import shopping.infrastructure.ExchangeRateApi;

@Configuration
public class TestConfig {

    @Bean
    @Profile("test")
    public ExchangeRateApi exchangeRateApi() {
        return () -> 1300.0;
    }
}
