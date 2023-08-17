package shopping.infrastructure;

import java.util.Optional;

public interface ExchangeRateProvider {

    Optional<Double> getFrom(final CurrencyCountry country);
}
