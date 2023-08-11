package shopping.exchange;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.infrastructure.ExchangeRateApi;

@Profile("test")
@Component
public class MockExchangeRateApi implements ExchangeRateApi {

    @Override
    public Double callExchangeRate() {
        return 1300.1;
    }
}
