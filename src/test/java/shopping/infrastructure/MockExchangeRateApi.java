package shopping.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.infrastructure.ExchangeRateApi;
import shopping.infrastructure.dto.ExchangeRateResponse;

@Component
@Profile("test")
public class MockExchangeRateApi implements ExchangeRateApi {

    @Override
    public ExchangeRateResponse callExchangeRate() {
        return new ExchangeRateResponse(1300.1);
    }
}
