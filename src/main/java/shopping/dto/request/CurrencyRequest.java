package shopping.dto.request;

import java.util.Map;

public class CurrencyRequest {

    private boolean success;

    private String source;

    private Map<String, Double> quotes;

    private CurrencyRequest() {
    }

    public CurrencyRequest(boolean success, String source, Map<String, Double> quotes) {
        this.success = success;
        this.source = source;
        this.quotes = quotes;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSource() {
        return source;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }

}
