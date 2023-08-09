package shopping.infrastructure;

import java.util.Map;

public class CurrencyLayerExchangeRateResponse {
    private Map<String, Double> quotes;

    public CurrencyLayerExchangeRateResponse() {
    }

    public CurrencyLayerExchangeRateResponse(Map<String, Double> quotes) {
        this.quotes = quotes;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }
}
