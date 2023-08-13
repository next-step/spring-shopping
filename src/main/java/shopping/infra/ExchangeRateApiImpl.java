package shopping.infra;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("prod")
public class ExchangeRateApiImpl implements ExchangeRateApi {

    private final ExchangeRateApiProperties exchangeRateApiProperties;
    private final RestTemplate restTemplate;

    public ExchangeRateApiImpl(
            final ExchangeRateApiProperties exchangeRateApiProperties,
            final RestTemplate restTemplate
    ) {
        this.exchangeRateApiProperties = exchangeRateApiProperties;
        this.restTemplate = restTemplate;
    }

    public double getUSDtoKRWExchangeRate() {
        final URI uri = exchangeRateApiProperties.getExchangeRateApiURI();
        final String quotes = exchangeRateApiProperties.getQuotes();
        final String sourceToTarget = exchangeRateApiProperties.getSourceToTarget();

        final JsonNode jsonNode = getForEntity(uri).getBody();
        validateJsonNodeIsNotNull(jsonNode);

        return jsonNode.path(quotes).path(sourceToTarget).asDouble(Double.POSITIVE_INFINITY);
    }

    private ResponseEntity<JsonNode> getForEntity(final URI uri) {
        final ResponseEntity<JsonNode> response = restTemplate.getForEntity(uri, JsonNode.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RestClientException("환율 API의 응답이 200 OK가 아닙니다.");
        }
        return response;
    }

    private void validateJsonNodeIsNotNull(final JsonNode jsonNode) {
        if (jsonNode == null) {
            throw new RestClientException("API의 결과를 제대로 응답 받지 못했습니다.");
        }
    }
}
