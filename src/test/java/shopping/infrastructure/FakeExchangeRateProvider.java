package shopping.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.application.ExchangeRateProvider;

@Component
@Profile("test")
public class FakeExchangeRateProvider implements ExchangeRateProvider {

    @Override
    public Double getExchangeRate() {
        return 1.0;
    }
}
