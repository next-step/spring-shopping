package shopping.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    public static final String CURRENCY_ERROR_CODE_REGEX = "20[0-1]";

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
        checkCurrencyApi(jsonNode);
        return getCurrency(jsonNode.getBody(), currencyConversion);
    }

    private ResponseEntity<JsonNode> getJsonNodeFrom(URI uri) {
        try {
            return restTemplate.getForEntity(uri, JsonNode.class);
        } catch (RestClientException ignore) {
            throw new ShoppingException(ErrorCode.INVALID_CONNECT_CURRENCY_API);
        }
    }

    private void checkCurrencyApi(ResponseEntity<JsonNode> jsonNode) {
        validateCurrencyCode(jsonNode);
        validateCurrencyApiConnection(jsonNode);
    }

    private void validateCurrencyCode(ResponseEntity<JsonNode> jsonNode) {
        Pattern pattern = Pattern.compile(CURRENCY_ERROR_CODE_REGEX);
        Matcher matcher = pattern.matcher(jsonNode.getStatusCode().toString());
        if (!jsonNode.getBody().get(API_CONNECT_SUCCESS).booleanValue() && matcher.matches()) {
            throw new ShoppingException(ErrorCode.INVALID_CONVERT_CURRENCY);
        }
    }

    private void validateCurrencyApiConnection(ResponseEntity<JsonNode> jsonNode) {
        if (!jsonNode.getBody().get(API_CONNECT_SUCCESS).booleanValue()) {
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
