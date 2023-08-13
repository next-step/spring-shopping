package shopping.infrastructure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shopping.application.ExchangeRateProvider;

import java.time.Duration;

@Component
@Profile("!test")
public class CurrencyLayerExchangeRateProvider implements ExchangeRateProvider {

    private static final String EXCHANGE_RATE_API_PATH = "/live";
    private static final String SOURCE_CURRENCY = "USD";
    private static final String TARGET_CURRENCY = "KRW";
    private static final int TIMEOUT_MILLIS = 1000;
    private static final int MAX_RETRY = 3;

    private final WebClient webClient;
    private final String accessKey;

    public CurrencyLayerExchangeRateProvider(@Qualifier("CurrencyLayer") final WebClient webClient,
                                             @Value("${currencylayer.access-key}") final String accessKey) {
        this.webClient = webClient;
        this.accessKey = accessKey;
    }

    public Double getExchangeRate() {
        return webClient.get().uri(builder -> builder.path(EXCHANGE_RATE_API_PATH)
                        .queryParam("source", SOURCE_CURRENCY)
                        .queryParam("currencies", TARGET_CURRENCY)
                        .queryParam("access_key", accessKey)
                        .build())
                .retrieve()
                .bodyToMono(CurrencyLayerExchangeRateResponse.class)
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS))
                .doOnNext(this::validateResponseCode)
                .map(response -> response.getQuotes().get(SOURCE_CURRENCY + TARGET_CURRENCY))
                .retry(MAX_RETRY)
                .block();
    }

    private void validateResponseCode(final CurrencyLayerExchangeRateResponse response) {
        if (!response.isSuccess() && !response.isRetryable()) {
            throw new IllegalStateException("환율 API 요청이 너무 많습니다.");
        }
    }
}
