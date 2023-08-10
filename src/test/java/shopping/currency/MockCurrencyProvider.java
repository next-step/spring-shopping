package shopping.currency;

import java.math.BigDecimal;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class MockCurrencyProvider implements CurrencyProvider {

    @Override
    public BigDecimal findUsdKrwCurrency() {
        return new BigDecimal("1300");
    }
}
