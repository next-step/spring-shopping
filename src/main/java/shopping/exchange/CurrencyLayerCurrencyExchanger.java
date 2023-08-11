package shopping.exchange;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CurrencyLayerCurrencyExchanger implements CurrencyExchanger {

    private final CurrencyLayerClient currencyLayerClient;
    private final CurrencyLayerParser currencyLayerParser;

    public CurrencyLayerCurrencyExchanger(
        final CurrencyLayerClient currencyLayerClient,
        final CurrencyLayerParser currencyLayerParser
    ) {
        this.currencyLayerClient = currencyLayerClient;
        this.currencyLayerParser = currencyLayerParser;
    }

    @Override
    public Optional<CurrencyExchangeRate> findCurrencyExchangeRate(
        final CurrencyType from,
        final CurrencyType to
    ) {
        validateSupportedExchangeType(from, to);

        final Map<String, Double> exchangeRateByCurrency =
            currencyLayerParser.parseExchangeRateMap(
                currencyLayerClient.sendLiveCurrencyExchangeRateRequest()
            );

        return Optional.ofNullable(
            new CurrencyExchangeRate(exchangeRateByCurrency.get(from.getName() + to.getName())
            ));
    }
}
