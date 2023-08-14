package shopping.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import shopping.config.TestConfig;
import shopping.infrastructure.CurrencyLayerExchangeRateApi;
import shopping.infrastructure.ExchangeRateApi;

@Configuration
@Import(TestConfig.class)
public class CurrencyLayerExchangeRateApiTestConfig {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${currency.accessKey}")
    private String accessKey;
    @Value("${currency.baseUrl}")
    private String baseurl;
    @Bean
    public ExchangeRateApi exchangeRateApi() {
        return new CurrencyLayerExchangeRateApi(
            accessKey,
            baseurl,
            restTemplate
        );
    }
}