package shopping.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import shopping.cart.component.DefaultExchangeRateProvider;
import shopping.cart.component.ExchangeRateProvider;

@Configuration
public class ExchangeRateProviderConfig {

    private final RestTemplate restTemplate;

    public ExchangeRateProviderConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean
    @Profile("!test")
    public ExchangeRateProvider defaultExchangeRateProvider() {
        return new DefaultExchangeRateProvider(restTemplate);
    }
}
