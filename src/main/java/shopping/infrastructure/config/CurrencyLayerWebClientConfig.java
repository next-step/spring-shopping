package shopping.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Profile("!test")
public class CurrencyLayerWebClientConfig {
    private final String baseUrl;

    public CurrencyLayerWebClientConfig(@Value("${currencylayer.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Bean
    @Qualifier("CurrencyLayer")
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
