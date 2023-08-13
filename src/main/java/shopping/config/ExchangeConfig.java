package shopping.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shopping.exchange.CurrencyExchangeClient;
import shopping.exchange.CurrencyExchangeParser;
import shopping.exchange.CurrencyExchanger;
import shopping.exchange.CurrencyLayerCurrencyExchangeClient;
import shopping.exchange.CurrencyLayerCurrencyExchangeParser;
import shopping.exchange.CurrencyLayerCurrencyExchanger;

@Configuration
public class ExchangeConfig {


    @Value("${currency-layer.base-url}")
    private String baseUrl;

    @Value("${currency-layer.access-key}")
    private String accessKey;

    private final ObjectMapper objectMapper;
    private final RestTemplateBuilder restTemplateBuilder;

    public ExchangeConfig(
        final ObjectMapper objectMapper,
        final RestTemplateBuilder restTemplateBuilder
    ) {
        this.objectMapper = objectMapper;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean
    public CurrencyExchanger currencyExchanger() {
        return new CurrencyLayerCurrencyExchanger(
            currencyExchangerClient(),
            currencyExchangeParser()
        );
    }

    @Bean
    public CurrencyExchangeClient currencyExchangerClient() {
        return new CurrencyLayerCurrencyExchangeClient(baseUrl, accessKey, restTemplateBuilder);
    }

    @Bean
    public CurrencyExchangeParser currencyExchangeParser() {
        return new CurrencyLayerCurrencyExchangeParser(objectMapper);
    }
}
