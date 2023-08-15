package shopping.cart.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import shopping.cart.domain.vo.ExchangeRate;
import shopping.cart.dto.response.ExchangeRateResponse;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.ShoppingException;

import java.util.Map;

public class DefaultExchangeRateProvider implements ExchangeRateProvider {

    private static final String REQUEST_URL = "http://api.currencylayer.com/live";
    private static final String KRW = "KRW";
    private static final String USDKRW = "USDKRW";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RestTemplate restTemplate;
    @Value("${currency-layer.secret-key}")
    private String accessKey;

    public DefaultExchangeRateProvider(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable(cacheNames = "exchangeRate")
    public ExchangeRate fetchExchangeRate() {
        logger.info("fetch exchange rate");
        final String requestUri = REQUEST_URL + "?currencies=" + KRW + "&access_key=" + accessKey;
        final ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(requestUri,
                                                                                        ExchangeRateResponse.class);
        validateFetch(response);
        final Map<String, Double> quotes = response.getBody().getQuotes();
        final Double exchangeRateValue = quotes.get(USDKRW);
        validateNotNull(exchangeRateValue);
        return new ExchangeRate(exchangeRateValue);
    }

    @CacheEvict(cacheNames = "exchangeRate", allEntries = true)
    @Scheduled(fixedRateString = "${cache.eviction.fixed-rate}", initialDelayString = "${cache.eviction.fixed-rate}")
    public void emptyCache() {
        logger.info("exchange rate cache evicted");
    }


    private void validateFetch(final ResponseEntity<ExchangeRateResponse> response) {
        if (!response.hasBody() || !response.getBody().isSuccess()) {
            throw new ShoppingException(ErrorCode.FAILED_TO_FETCH_EXCHANGE_RATE);
        }
    }

    private void validateNotNull(final Double rawExchangeRate) {
        if (rawExchangeRate == null) {
            throw new ShoppingException(ErrorCode.FAILED_TO_FETCH_EXCHANGE_RATE);
        }
    }
}
