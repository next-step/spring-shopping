package shopping.currency;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.order.domain.vo.ExchangeRate;

@Profile("test")
@Component
public class MockExchangeRateProvider implements ExchangeRateProvider {

    @Override
    public ExchangeRate findTargetExchangeRate() {
        return new ExchangeRate("1300");
    }
}
