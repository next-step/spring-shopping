package shopping.exchange;

import java.util.Map;
import java.util.Optional;

public class CurrencyLayerCurrencyExchanger implements CurrencyExchanger {

    private final CurrencyExchangeClient currencyExchangeClient;
    private final CurrencyExchangeParser currencyExchangeParser;

    public CurrencyLayerCurrencyExchanger(
        final CurrencyExchangeClient currencyExchangeClient,
        final CurrencyExchangeParser currencyExchangeParser
    ) {
        this.currencyExchangeClient = currencyExchangeClient;
        this.currencyExchangeParser = currencyExchangeParser;
    }

    @Override
    public Optional<CurrencyExchangeRate> findCurrencyExchangeRate(
        final CurrencyType from,
        final CurrencyType to
    ) {
        validateSupportedExchangeType(from, to);

        final Map<String, Double> exchangeRateByCurrency = currencyExchangeParser
            .parseExchangeRateMap(
                currencyExchangeClient.sendLiveCurrencyExchangeRateRequest());

        return Optional.of(
            new CurrencyExchangeRate(exchangeRateByCurrency.get(from.getName() + to.getName())));
    }
}
