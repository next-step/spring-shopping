package shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import shopping.order.service.ExchangeRateService;
import shopping.other.exchange.currencylayer.MockExchangeRateServiceImpl;

@Configuration
@Profile("test")
public class TestOtherApiConfiguration {

    @Bean
    public ExchangeRateService exchangeRateServiceTest() {
        return new MockExchangeRateServiceImpl();
    }
}
