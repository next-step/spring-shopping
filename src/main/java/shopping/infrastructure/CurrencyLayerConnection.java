package shopping.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shopping.dto.response.ExchangeRateErrorResponse;
import shopping.dto.response.ExchangeRateResponse;
import shopping.exception.infrastructure.ConnectionFailException;
import shopping.exception.infrastructure.NoConnectionException;

@Component
public class CurrencyLayerConnection {

    private static final String BASE_URL = "http://apilayer.net/api/live";

    @Value("${security.currency-layer.access-key}")
    private String accessKey;

    private final WebClient client;

    public CurrencyLayerConnection() {
        this.client = WebClient.create(BASE_URL);
    }

    public Double getExchangeRate(String source, String target) {
        ExchangeRateResponse response = getExchangeRateResponse(source, target);
        if (response.isSuccess()) {
            return response.getExchangeRates().get(source+target);
        }
        ExchangeRateErrorResponse error = response.getError();
        throw new ConnectionFailException(error.getInformation(), error.getCode());
    }

    private ExchangeRateResponse getExchangeRateResponse(String source, String target) {
        ExchangeRateResponse response = client.get()
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

    private void validateNotNullResponse(ExchangeRateResponse response) {
        if (response == null) {
            throw new NoConnectionException();
        }
    }
}
