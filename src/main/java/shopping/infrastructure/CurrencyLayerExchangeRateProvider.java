package shopping.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shopping.application.ExchangeRateProvider;

import java.util.Optional;

@Component
@Profile("!test")
public class CurrencyLayerExchangeRateProvider implements ExchangeRateProvider {

    private static final String EXCHANGE_RATE_API_PATH = "/live";
    private static final String SOURCE_CURRENCY = "USD";
    private static final String TARGET_CURRENCY = "KRW";

    private String baseUrl;
    private String accessKey;

    public CurrencyLayerExchangeRateProvider(@Value("${currencylayer.base-url}") String baseUrl,
                                             @Value("${currencylayer.access-key}") String accessKey) {
        this.baseUrl = baseUrl;
        this.accessKey = accessKey;
    }

    public Optional<Double> getExchangeRate() {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get().uri(builder -> builder.path(EXCHANGE_RATE_API_PATH)
                        .queryParam("source", SOURCE_CURRENCY)
                        .queryParam("currencies", TARGET_CURRENCY)
                        .queryParam("access_key", accessKey)
                        .build()
                ).retrieve().bodyToMono(CurrencyLayerExchangeRateDto.class)
                .map(dto -> dto.getQuotes().get(SOURCE_CURRENCY + TARGET_CURRENCY))
                .blockOptional();
    }
}
