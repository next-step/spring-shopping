package shopping.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shopping.domain.CurrencyConversion;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@Service
public class CurrencyConverter implements CurrencyService {

    private static final long CACHE_EVICT_TIME = 3l;
    public static final String API_CONNECT_SUCCESS = "success";

    @Value("${currency.access-key}")
    private String CURRENCY_ACCESS_KEY;
    @Value("${currency.source-url}")
    private String CURRENCY_SOURCE_URL;

    private final RestTemplate restTemplate;

    public CurrencyConverter() {
        restTemplate = new RestTemplate();
    }

    @Override
    @Cacheable(value = "currency")
    public double getCurrencyFrom(CurrencyConversion currencyConversion) {
        URI uri = getUri(currencyConversion);
        ResponseEntity<JsonNode> jsonNode = getJsonNodeFrom(uri);
        HttpStatus statusCode = Optional.of(jsonNode.getStatusCode())
            .orElseThrow(() -> new ShoppingException(ErrorCode.INVALID_CONNECT_CURRENCY_API));
        JsonNode jsonNodeBody = Optional.ofNullable(jsonNode.getBody())
            .orElseThrow(() -> new ShoppingException(ErrorCode.INVALID_CONNECT_CURRENCY_API));
        checkCurrencyApi(statusCode, jsonNodeBody);
        return getCurrency(jsonNodeBody, currencyConversion);
    }

    private ResponseEntity<JsonNode> getJsonNodeFrom(URI uri) {
        try {
            return restTemplate.getForEntity(uri, JsonNode.class);
        } catch (RestClientException ignore) {
            throw new ShoppingException(ErrorCode.INVALID_CONNECT_CURRENCY_API);
        }
    }

    private void checkCurrencyApi(HttpStatus statusCode, JsonNode jsonNodeBody) {
        boolean is2xxSuccessful = statusCode.is2xxSuccessful();
        boolean isFailedMessage = Optional.ofNullable(!jsonNodeBody.get(API_CONNECT_SUCCESS).booleanValue())
            .orElse(true);
        validateCurrencyCode(is2xxSuccessful, isFailedMessage);
        validateCurrencyApiConnection(isFailedMessage);
    }

    private void validateCurrencyCode(boolean is2xxSuccessful, boolean isFailMessage) {
        if (isFailMessage && is2xxSuccessful) {
            throw new ShoppingException(ErrorCode.INVALID_CONVERT_CURRENCY);
        }
    }

    private void validateCurrencyApiConnection(boolean isFailMessage) {
        if (isFailMessage) {
            throw new ShoppingException(ErrorCode.INVALID_CONNECT_CURRENCY_API);
        }
    }

    @CacheEvict(value = "currency", allEntries = true)
    @Scheduled(fixedDelay = CACHE_EVICT_TIME, timeUnit = TimeUnit.SECONDS)
    public void cacheEvictInCurrency() {
    }

    private double getCurrency(JsonNode jsonNode, CurrencyConversion currencyConversion) {
        return jsonNode
            .get("quotes")
            .get(currencyConversion.getSourceTarget())
            .asDouble();
    }

    private URI getUri(CurrencyConversion currencyConversion) {
        return UriComponentsBuilder.fromHttpUrl(CURRENCY_SOURCE_URL)
            .queryParam("access_key", CURRENCY_ACCESS_KEY)
            .queryParam("currencies", currencyConversion.getTarget())
            .queryParam("source", currencyConversion.getSource())
            .build()
            .toUri();
    }

}
