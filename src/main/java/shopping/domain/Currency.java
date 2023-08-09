package shopping.domain;

import java.util.Map;

public class Currency {

    private boolean success;

    private String source;

    private Map<String, Double> quotes;

    private Currency() {
    }

    public Currency(boolean success, String source, Map<String, Double> quotes) {
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

    @Override
    public String toString() {
        return "Currency{" +
            "success=" + success +
            ", source='" + source + '\'' +
            ", quotes=" + quotes +
            '}';
    }
}
