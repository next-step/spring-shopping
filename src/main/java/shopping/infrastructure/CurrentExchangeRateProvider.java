package shopping.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shopping.application.ExchangeRateProvider;
import shopping.domain.ExchangeRate;
import shopping.dto.response.CurrencyResponse;

@Component
public class CurrentExchangeRateProvider implements ExchangeRateProvider {

    private static final String apiUrl = "http://apilayer.net/api/live?access_key=";

    private final String apiAccessKey;

    public CurrentExchangeRateProvider(@Value("${shopping.currency.apiKey}") String apiAccessKey) {
        this.apiAccessKey = apiAccessKey;
    }

    @Override
    public ExchangeRate getExchange(String quote) {
        CustomRestTemplate customRestTemplate = new CustomRestTemplate();
        CurrencyResponse currencyInfo = customRestTemplate.getResult(apiUrl + apiAccessKey, CurrencyResponse.class);
        double usdCurrency = currencyInfo.getByQuote(quote);
        return new ExchangeRate(usdCurrency);
    }
}
