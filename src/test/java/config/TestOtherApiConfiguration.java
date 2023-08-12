package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import shopping.order.service.ExchangeRateService;
import shopping.other.exchange.MockExchangeRateServiceImpl;

@Configuration
public class TestOtherApiConfiguration {

    @Bean
    @Profile("test")
    public ExchangeRateService exchangeRateServiceTest() {
        return new MockExchangeRateServiceImpl();
    }
}
