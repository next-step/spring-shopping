package shopping.dto;

import java.util.Map;

public class CurrencyLayerResponse {

    private Map<String, Double> quotes;

    private CurrencyLayerResponse() {
    }

    public CurrencyLayerResponse(Map<String, Double> quotes) {
        this.quotes = quotes;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }
}
