package shopping.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static shopping.infrastructure.CurrencyCountry.KOREA;

@Component
public class CurrencyLayerExchangeRateProvider implements ExchangeRateProvider {

    private static final Logger log = LoggerFactory.getLogger(CurrencyLayerExchangeRateProvider.class.getSimpleName());

    private static final String QUOTES = "quotes";
    private static final String API_URL = "http://apilayer.net/api/live";

    private final String accessKey;
    private final RestTemplate restTemplate;

    public CurrencyLayerExchangeRateProvider(@Value("${currency-layer.access-key}") final String accessKey,
                                             final RestTemplate restTemplate) {
        this.accessKey = accessKey;
        this.restTemplate = restTemplate;
    }

    @Override
    public double getFrom(final CurrencyCountry country) {
        final URI uri = createUri(country.getCurrencyLayerName());

        try {
            log.info("환율 서버와 통신 ({})", uri);
            final ResponseEntity<JsonNode> response = restTemplate.getForEntity(uri, JsonNode.class);
            validateStatusCode(response.getStatusCode());

            final JsonNode body = findBody(response);
            return getExchangeRate(country, body);
        } catch (final RestClientException exception) {
            log.error("환율 서버와 통신 중 에러가 발생했습니다");
            throw new IllegalStateException();
        }
    }

    private double getExchangeRate(final CurrencyCountry country, final JsonNode body) {
        if (checkSuccess(body)) {
            return findResult(body, country.getCurrencyLayerName()).asDouble();
        }
        return Double.MIN_VALUE;
    }

    private JsonNode findBody(final ResponseEntity<JsonNode> response) {
        if (Optional.ofNullable(response.getBody()).isEmpty()) {
            log.error("응답이 존재하지 않습니다");
            throw new IllegalArgumentException();
        }
        return response.getBody();
    }

    private boolean checkSuccess(final JsonNode body) {
        if (!body.get("success").asBoolean(false)) {
            log.error("환율 서버에서 에러가 발생했습니다\n{}", body.get("error"));
            return false;
        }
        return true;
    }

    private static JsonNode findResult(final JsonNode response, final String country) {
        try {
            return response
                    .path(QUOTES)
                    .path(country.concat(KOREA.getCurrencyLayerName()));
        } catch (final NullPointerException exception) {
            log.error("환율 서버에서 원하는 결과를 찾지 못했습니다");
            throw new IllegalArgumentException();
        }
    }

    private URI createUri(final String countryName) {
        return UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("access_key", accessKey)
                .queryParam("currencies", KOREA.getCurrencyLayerName())
                .queryParam("source", countryName)
                .queryParam("format", 1)
                .encode()
                .build().toUri();
    }

    private void validateStatusCode(final HttpStatus statusCode) {
        if (!statusCode.is2xxSuccessful()) {
            log.error("환율 서버로부터 실패한 응답을 받았습니다");
            throw new IllegalStateException();
        }
    }
}
