package shopping.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import shopping.dto.api.ExchangeRateResponse;
import shopping.dto.web.response.ExchangeRateErrorResponse;
import shopping.exception.infrastructure.ConnectionErrorException;
import shopping.exception.infrastructure.NullResponseException;

import java.time.Duration;

@Component
public class CurrencyLayerConnection implements ExchangeRateFetcher {

    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Duration RETRY_DELAY = Duration.ofMillis(500);

    @Value("${security.currency-layer.access-key}")
    private String accessKey;

    private final WebClient webClient;

    public CurrencyLayerConnection(WebClient webClient) {
        this.webClient = webClient;
    }

    public Double getExchangeRate(String source, String target) throws ConnectionErrorException, NullResponseException {
        ExchangeRateResponse response = getExchangeRateResponse(source, target);
        if (response.isSuccess()) {
            return response.getExchangeRates().get(source+target);
        }
        ExchangeRateErrorResponse error = response.getError();
        throw new ConnectionErrorException(error.getInformation(), error.getCode());
    }

    private ExchangeRateResponse getExchangeRateResponse(String source, String target) throws NullResponseException {
        ExchangeRateResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("access_key", accessKey)
                        .queryParam("currencies", target)
                        .queryParam("source", source)
                        .build())
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .retryWhen(Retry.fixedDelay(MAX_RETRY_ATTEMPTS, RETRY_DELAY))
                .block();
        validateNotNullResponse(response);
        return response;
    }

    private void validateNotNullResponse(ExchangeRateResponse response) throws NullResponseException {
        if (response == null) {
            throw new NullResponseException();
        }
    }
}
