package shopping.infrastructure;

import java.time.Duration;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import shopping.domain.cart.CurrencyType;
import shopping.dto.api.ExchangeRateResponse;
import shopping.dto.web.response.ExchangeRateErrorResponse;
import shopping.exception.infrastructure.ErrorResponseException;
import shopping.exception.infrastructure.NullResponseException;

@Component
public class CurrencyLayerConnection implements ExchangeRateFetcher {

    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Duration RETRY_DELAY = Duration.ofMillis(500);

    private final Logger log = LoggerFactory.getLogger(CurrencyLayerConnection.class);

    @Value("${security.currency-layer.access-key}")
    private String accessKey;

    private final WebClient webClient;

    public CurrencyLayerConnection(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Optional<Double> getExchangeRate(CurrencyType source, CurrencyType target) {
        try{
            return Optional.ofNullable(fetchExchangeRate(source, target));
        } catch (NullResponseException e) {
            log.error(e.getMessage());
        } catch (ErrorResponseException e) {
            log.error("code: {}, info: {}", e.getErrorCode(), e.getMessage());
        }
        return Optional.empty();
    }

    private Double fetchExchangeRate(CurrencyType source, CurrencyType target) throws NullResponseException, ErrorResponseException {
        ExchangeRateResponse response = getExchangeRateResponse(source.getType(), target.getType());
        if (response.isSuccess()) {
            return response.getExchangeRates().get(source.getType()+target.getType());
        }
        ExchangeRateErrorResponse error = response.getError();
        throw new ErrorResponseException(error.getInformation(), error.getCode());
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
