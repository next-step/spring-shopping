package shopping.infrastructure;

import java.util.Optional;
import shopping.domain.cart.CurrencyType;

public interface ExchangeRateFetcher {

    Optional<Double> getExchangeRate(CurrencyType source, CurrencyType target);
}
