package shopping.order.infra.exchange;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import shopping.order.app.spi.ExchangeVendor;

@Configuration
@Profile("production")
public class CurrencyLayerConfigurer {

    private static final int MAXIMUM_WAIT_SECONDS = 10;

    @Qualifier("currencyRestTemplate")
    RestTemplate restTemplate;

    @Bean
    public ExchangeVendor currencyLayerExchangeVendor(@Value("${exchange.vendor.currency.url}") String url,
            @Value("${exchange.vendor.currency.secret-key}") String accessKey) {
        return new CurrencyLayerExchangeVendor(currencyRestTemplate(), url, accessKey);
    }

    @Bean
    public RestTemplate currencyRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(MAXIMUM_WAIT_SECONDS))
                .setReadTimeout(Duration.ofSeconds(MAXIMUM_WAIT_SECONDS))
                .build();
    }
}
