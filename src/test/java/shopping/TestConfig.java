package shopping;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import shopping.cart.component.ExchangeRateProvider;
import shopping.cart.component.MockExchangeRateProvider;

@TestConfiguration
public class TestConfig {

    @Bean
    public ExchangeRateProvider mockExchangeRateProvider() {
        return new MockExchangeRateProvider();
    }
}
