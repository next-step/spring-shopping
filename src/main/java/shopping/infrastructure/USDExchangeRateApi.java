package shopping.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import java.net.URI;

@Component
public class USDExchangeRateApi implements ExchangeRateApi {

    private static final String QUOTES = "quotes";
    private static final String USD_KRW = "USDKRW";
    private static final String ACCESS_KEY = "access_key";
    private final String currencyApiUrl;
    private final RestTemplate restTemplate;
    private final String accessKey;

    public USDExchangeRateApi(
            final RestTemplate restTemplate,
            @Value("${currency-api.url}") final String currencyApiUrl,
            @Value("${currency-api.key}") final String accessKey
    ) {
        this.restTemplate = restTemplate;
        this.currencyApiUrl = currencyApiUrl;
        this.accessKey = accessKey;
    }

    @Override
    public double getRealTimeExchangeRate() {
        final ResponseEntity<JsonNode> response = restTemplate.getForEntity(uriBuild(), JsonNode.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            final JsonNode body = response.getBody();
            return getUSDKRW(body).asDouble();
        }

        throw new ShoppingException(ErrorCode.CURRENCY_API_ERROR);
    }

    private JsonNode getUSDKRW(final JsonNode body) {
        if (body == null) {
            throw new ShoppingException(ErrorCode.CURRENCY_API_ERROR);
        }

        return body.path(QUOTES).get(USD_KRW);

    }

    private URI uriBuild() {
        return UriComponentsBuilder.fromHttpUrl(currencyApiUrl)
                .queryParam(ACCESS_KEY, accessKey)
                .build()
                .toUri();
    }
}
