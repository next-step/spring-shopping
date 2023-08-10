package shopping.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Component
public class CurrencyLayerExchangeRateProvider implements ExchangeRateProvider {

    private static final String QUOTES = "quotes";
    private static final String API_URL = "http://api.currencylayer.com/live";
    private static final String KOREA = CurrencyCountry.KOREA.getCurrencyLayerName();

    private final String accessKey;
    private final RestTemplate restTemplate;

    protected CurrencyLayerExchangeRateProvider(@Value("${currency-layer.access-key}") final String accessKey) {
        this.accessKey = accessKey;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public double getFrom(final CurrencyCountry country) {
        final URI uri = createUri();

        try {
            final ResponseEntity<JsonNode> response = restTemplate.getForEntity(uri, JsonNode.class);
            validateStatusCode(response.getStatusCode());
            final JsonNode body = findSuccessBody(response);

            return findResult(body, country.getCurrencyLayerName()).asDouble(Double.NEGATIVE_INFINITY);
        } catch (final RestClientException exception) {
            throw new IllegalStateException();
        }
    }

    private JsonNode findSuccessBody(final ResponseEntity<JsonNode> response) {
        final JsonNode body = Objects.requireNonNull(response.getBody());
        validateSuccess(body);
        return body;
    }

    private void validateSuccess(final JsonNode body) {
        if (!body.get("success").asBoolean(false)) {
            throw new IllegalStateException();
        }
    }

    private static JsonNode findResult(final JsonNode response, final String country) {
        try {
            return response
                    .path(QUOTES)
                    .path(country.concat(KOREA));
        } catch (final NullPointerException exception) {
            throw new IllegalArgumentException();
        }
    }

    private URI createUri() {
        return UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("access_key", accessKey)
                .encode()
                .build().toUri();
    }

    private void validateStatusCode(final HttpStatus statusCode) {
        if (!statusCode.is2xxSuccessful()) {
            throw new IllegalStateException();
        }
    }
}
