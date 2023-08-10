package shopping.infra;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.fixture.OrderFixture;

@Component
@Profile("test")
public class MockExchangeRateApi implements ExchangeRateApi {

    @Override
    public double getUSDtoKRWExchangeRate() {
        return OrderFixture.DEFAULT_EXCHANGE_RATE;
    }
}
