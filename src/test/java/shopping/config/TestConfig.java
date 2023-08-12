package shopping.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import shopping.infrastructure.CurrencyLayerExchangeRateApi;
import shopping.infrastructure.ExchangeRateApi;

@Configuration
@Profile("test")
public class TestConfig {

    private static final int CONNECTION_TIME_OUT = 5000;
    private static final int READ_TIME_OUT = 5000;
    public static final int BACK_OFF_PERIOD = 1000;
    private static final int MAX_ATTEMPT = 3;

    @Value("${currency.accessKey}")
    private String accessKey;
    @Value("${currency.baseUrl}")
    private String baseurl;


    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }


    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder
            .requestFactory(
                () -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
            .setConnectTimeout(Duration.ofMillis(CONNECTION_TIME_OUT))
            .setReadTimeout(Duration.ofMillis(READ_TIME_OUT))
            .build();
    }


    @Bean
    public ExchangeRateApi exchangeRateApi() {
        return new CurrencyLayerExchangeRateApi(
            accessKey,
            baseurl,
            restTemplate(restTemplateBuilder())
        );
    }
}