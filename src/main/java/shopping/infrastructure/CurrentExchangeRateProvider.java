package shopping.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping.application.ExchangeRateProvider;
import shopping.domain.ExchangeCode;
import shopping.domain.ExchangeRate;
import shopping.domain.Currency;
import shopping.dto.response.ExchangeResponse;
import shopping.exception.CurrencyException;
import shopping.exception.InfraException;

@Component
public class CurrentExchangeRateProvider implements ExchangeRateProvider {

    private static final String apiUrl = "http://apilayer.net/api/live?access_key=";

    private final String apiAccessKey;
    private final CustomRestTemplate customRestTemplate;

    public CurrentExchangeRateProvider(@Value("${shopping.currency.apiKey}") String apiAccessKey,
            CustomRestTemplate customRestTemplate) {
        this.apiAccessKey = apiAccessKey;
        this.customRestTemplate = customRestTemplate;
    }

    @Override
    public ExchangeRate getExchange(ExchangeCode code) {
        try {
            Currency currency = initializeCurrency();
            double usdCurrency = currency.getByCode(code);
            return new ExchangeRate(usdCurrency);
        } catch (CurrencyException exception) {
            throw new InfraException(exception.getMessage());
        }
    }

    private Currency initializeCurrency() {
        ExchangeResponse response = customRestTemplate.getResult(apiUrl + apiAccessKey, ExchangeResponse.class);
        return new Currency(response.getQuotes());
    }
}
