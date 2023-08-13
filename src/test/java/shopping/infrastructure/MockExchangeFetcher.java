package shopping.infrastructure;

import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.domain.cart.CurrencyType;
import shopping.exception.infrastructure.ErrorResponseException;
import shopping.exception.infrastructure.NullResponseException;

@Profile("test")
@Primary
@Component
public class MockExchangeFetcher implements ExchangeRateFetcher {

    private static final CurrencyType SOURCE = CurrencyType.USD;
    private static final CurrencyType TARGET = CurrencyType.KRW;

    @Override
    public Optional<Double> getExchangeRate(CurrencyType source, CurrencyType target) {
        if (source.equals(SOURCE) && target.equals(TARGET)) {
            return Optional.of(1300.0);
        }
        return Optional.empty();
    }

}
