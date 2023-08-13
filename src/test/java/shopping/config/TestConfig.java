package shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import shopping.service.ExchangeRateService;

@Configuration
public class TestConfig {

    @Bean
    @Profile("test")
    public ExchangeRateService exchangeRateApi() {
        return (time) -> 1300.0;
    }
}
