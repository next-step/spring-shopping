package shopping.dto.response;

import java.util.Map;

public class CurrencyResponse {

    Map<String, Double> quotes;

    private CurrencyResponse() {
    }

    public CurrencyResponse(Map<String, Double> quotes) {
        this.quotes = quotes;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }

    public double getByQuote(String quote) {
        return quotes.get(quote);
    }
}
