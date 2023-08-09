package shopping.infrastructure;

import java.util.Map;

public class CurrencyLayerExchangeRateDto {
    private Map<String, Double> quotes;

    public CurrencyLayerExchangeRateDto() {
    }

    public CurrencyLayerExchangeRateDto(Map<String, Double> quotes) {
        this.quotes = quotes;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }
}
