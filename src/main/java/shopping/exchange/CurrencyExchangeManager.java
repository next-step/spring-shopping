package shopping.exchange;

import java.util.Optional;

public interface CurrencyExchangeManager {

    Optional<Double> findCurrencyExchangeRate(final CurrencyType from, final CurrencyType to);
}
