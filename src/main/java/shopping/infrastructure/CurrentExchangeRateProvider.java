package shopping.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping.application.ExchangeRateProvider;
import shopping.domain.ExchangeRate;
import shopping.domain.Currency;
import shopping.exception.CurrencyException;
import shopping.exception.InfraException;

@Component
public class CurrentExchangeRateProvider implements ExchangeRateProvider {

    private static final String apiUrl = "http://apilayer.net/api/live?access_key=";

    private final String apiAccessKey;

    public CurrentExchangeRateProvider(@Value("${shopping.currency.apiKey}") String apiAccessKey) {
        this.apiAccessKey = apiAccessKey;
    }

    @Override
    public ExchangeRate getExchange(String quote) {
        try {
            Currency currency = initializeCurrency();
            double usdCurrency = currency.getByQuote(quote);
            return new ExchangeRate(usdCurrency);
        } catch (CurrencyException exception) {
            throw new InfraException(exception.getMessage());
        }
    }

    private Currency initializeCurrency() {
        CustomRestTemplate customRestTemplate = new CustomRestTemplate();
        return customRestTemplate.getResult(apiUrl + apiAccessKey, Currency.class);
    }
}
