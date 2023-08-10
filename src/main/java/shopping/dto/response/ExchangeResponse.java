package shopping.dto.response;

import java.util.Map;

public class ExchangeResponse {

    private Map<String, Double> quotes;

    public ExchangeResponse(Map<String, Double> quotes) {
        this.quotes = quotes;
    }

    private ExchangeResponse() {
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }
}
