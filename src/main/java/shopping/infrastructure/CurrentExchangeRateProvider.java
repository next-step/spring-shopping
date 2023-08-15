package shopping.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping.application.ExchangeRateProvider;
import shopping.domain.EmptyExchangeRates;
import shopping.domain.ExchangeCode;
import shopping.domain.ExchangeRate;
import shopping.domain.ExchangeRates;
import shopping.dto.response.ExchangeResponse;
import shopping.exception.CurrencyException;
import shopping.exception.InfraException;

@Component
public class CurrentExchangeRateProvider implements ExchangeRateProvider {

    private static final String API_URL = "http://apilayer.net/api/live?access_key=";

    private final String apiAccessKey;
    private final CacheProvider cache;
    private final CustomRestTemplate customRestTemplate;

    public CurrentExchangeRateProvider(@Value("${shopping.currency.apiKey}") String apiAccessKey,
                                       CustomRestTemplate customRestTemplate,
                                       CacheProvider cacheProvider) {
        this.cache = cacheProvider;
        this.apiAccessKey = apiAccessKey;
        this.customRestTemplate = customRestTemplate;
    }

    @Override
    public ExchangeRate getExchange(ExchangeCode code) {
        try {
            putIfAbsent();
            final ExchangeRates exchangeRates = cache.get(ExchangeRates.class);
            return exchangeRates.getRate(code);
        } catch (CurrencyException exception) {
            throw new InfraException(exception.getMessage());
        }
    }

    private void putIfAbsent() {
        if (!cache.isContains(ExchangeRates.class)) {
            cache.put(ExchangeRates.class, this::initializeExchangeRates);
        }
    }

    private ExchangeRates initializeExchangeRates() {
        return customRestTemplate.getResult(API_URL + apiAccessKey, ExchangeResponse.class)
            .map(exchangeResponse -> new ExchangeRates(exchangeResponse.getQuotes()))
            .orElseGet(EmptyExchangeRates::new);
    }
}
