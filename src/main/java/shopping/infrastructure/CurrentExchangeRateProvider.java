package shopping.infrastructure;

import org.springframework.stereotype.Component;
import shopping.application.ExchangeRateProvider;
import shopping.domain.ExchangeRate;

@Component
public class CurrentExchangeRateProvider implements ExchangeRateProvider {

    private static final String ACCESS_KEY = "b87f1d7c0299ae3f62ea4881b6893501";
    private static final String API_URL = "http://apilayer.net/api/live?access_key" + ACCESS_KEY;

    @Override
    public ExchangeRate getExchange() {
        return new ExchangeRate(1002.1);
    }
}
