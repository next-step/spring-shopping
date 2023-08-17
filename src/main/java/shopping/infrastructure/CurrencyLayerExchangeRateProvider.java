package shopping.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
    private Double cache;

    public CurrencyLayerExchangeRateProvider(@Value("${currency-layer.access-key}") final String accessKey,
                                             final RestTemplate restTemplate) {
        this.accessKey = accessKey;
        this.restTemplate = restTemplate;
        this.cache = null;
    }

    @Override
    public Optional<Double> getFrom(final CurrencyCountry country) {
        final URI uri = createUri(country.getCurrencyLayerName());

        try {
            log.info("환율 서버와 통신\n({})", uri);
            final ResponseEntity<JsonNode> response = restTemplate.getForEntity(uri, JsonNode.class);
            validateStatusCode(response.getStatusCode());

            final JsonNode body = findBody(response);
            validateBody(body);

            final Optional<Double> exchangeRate = getExchangeRate(country, body);
            renewCache(exchangeRate);
            return exchangeRate;
        } catch (final RuntimeException exception) {
            return Optional.ofNullable(cache);
        }
    }

    private void renewCache(final Optional<Double> exchangeRate) {
        exchangeRate.ifPresent(rate -> cache = rate);
    }

    private void validateBody(final JsonNode body) {
        if (!body.get("success").asBoolean(false)) {
            log.error("환율 서버에서 실패한 응답 반환\n응답 내용: {}", body.get("error"));
            throw new IllegalArgumentException();
        }
    }

    private Optional<Double> getExchangeRate(final CurrencyCountry country, final JsonNode body) {
        return Optional.of(findResult(body, country.getCurrencyLayerName()).asDouble());
    }

    private JsonNode findBody(final ResponseEntity<JsonNode> response) {
        return Optional.ofNullable(response.getBody()).orElseThrow(
                () -> {
                    log.error("비어있는 응답 반환");
                    return new IllegalArgumentException();
                }
        );
    }

    private static JsonNode findResult(final JsonNode response, final String country) {
        try {
            return response
                    .path(QUOTES)
                    .path(country.concat(KOREA.getCurrencyLayerName()));
        } catch (final NullPointerException exception) {
            log.error("응답 파싱 에러\n응답 - {}", response);
            throw new IllegalArgumentException();
        }
    }

    private void validateStatusCode(final HttpStatus statusCode) {
        if (!statusCode.is2xxSuccessful()) {
            log.error("실패한 응답 - {}", statusCode);
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
}
