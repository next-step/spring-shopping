package shopping.fake;

import java.util.Optional;
import shopping.exchange.CurrencyExchangeRate;
import shopping.exchange.CurrencyExchanger;
import shopping.exchange.CurrencyType;

public class FakeCurrencyExchanger implements CurrencyExchanger {

    @Override
    public Optional<CurrencyExchangeRate> findCurrencyExchangeRate(
        final CurrencyType from,
        final CurrencyType to
    ) {
        validateSupportedExchangeType(from, to);

        return Optional.of(new CurrencyExchangeRate(1300.0));
    }
}
