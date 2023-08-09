package shopping.infrastructure;

import org.springframework.stereotype.Component;
import shopping.application.ExchangeRateProvider;
import shopping.domain.ExchangeRate;
import shopping.dto.response.CurrencyResponse;

@Component
public class CurrentExchangeRateProvider implements ExchangeRateProvider {

    private static final String ACCESS_KEY = "b87f1d7c0299ae3f62ea4881b6893501";
    private static final String API_URL = "http://apilayer.net/api/live?access_key=" + ACCESS_KEY;

    @Override
    public ExchangeRate getExchange(String quote) {
        CustomRestTemplate customRestTemplate = new CustomRestTemplate();
        CurrencyResponse currencyInfo = customRestTemplate.getResult(API_URL, CurrencyResponse.class);
        double usdCurrency = currencyInfo.getByQuote(quote);
        return new ExchangeRate(usdCurrency);
    }
}
