package shopping.infrastructure;

import java.time.Clock;
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
                                       Clock clock) {
        this.cache = new CacheProvider(clock);
        cache.put(ExchangeRates.class, this::initializeExchangeRates);
        this.apiAccessKey = apiAccessKey;
        this.customRestTemplate = customRestTemplate;
    }

    @Override
    public ExchangeRate getExchange(ExchangeCode code) {
        try {
            final ExchangeRates exchangeRates = cache.get(ExchangeRates.class);
            return exchangeRates.getRate(code);
        } catch (CurrencyException exception) {
            throw new InfraException(exception.getMessage());
        }
    }

    private ExchangeRates initializeExchangeRates() {
        return customRestTemplate.getResult(API_URL + apiAccessKey, ExchangeResponse.class)
            .map(exchangeResponse -> new ExchangeRates(exchangeResponse.getQuotes()))
            .orElseGet(EmptyExchangeRates::new);
    }
}
