package shopping.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import shopping.exchange.CurrencyExchanger;
import shopping.fake.FakeCurrencyExchanger;

@TestConfiguration
public class TestConfig {

    @Bean
    public CurrencyExchanger currencyExchanger() {
        return new FakeCurrencyExchanger();
    }
}
