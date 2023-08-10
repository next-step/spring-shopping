package shopping.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shopping.dto.api.ExchangeRateResponse;
import shopping.dto.web.response.ExchangeRateErrorResponse;
import shopping.exception.infrastructure.ConnectionErrorException;
import shopping.exception.infrastructure.NullResponseException;

@Component
public class CurrencyLayerConnection implements ExchangeRateFetcher {

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
