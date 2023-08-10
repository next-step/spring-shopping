package shopping.currency;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.order.domain.vo.ExchangeRate;

@Profile("test")
@Component
public class MockCurrencyProvider implements CurrencyProvider {

    @Override
    public ExchangeRate findUsdKrwCurrency() {
        return new ExchangeRate("1300");
    }
}
