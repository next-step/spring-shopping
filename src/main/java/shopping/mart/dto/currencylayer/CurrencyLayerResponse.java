package shopping.mart.dto.currencylayer;

import java.util.Map;
import java.util.Objects;

public class CurrencyLayerResponse {

    private boolean success;
    private long timestamp;
    private String source;
    private Map<String, Double> quotes;

    public CurrencyLayerResponse() {
    }

    public CurrencyLayerResponse(final boolean success, final long timestamp, final String source,
                                 final Map<String, Double> quotes) {
        this.success = success;
        this.timestamp = timestamp;
        this.source = source;
        this.quotes = quotes;
    }

    public Double getCurrency(final String key) {
        if (Objects.isNull(quotes)) {
            return null;
        }

        return quotes.getOrDefault(key, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSource() {
        return source;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }

    @Override
    public String toString() {
        return "CurrencyLayerResponse{" +
            "success=" + success +
            ", timestamp=" + timestamp +
            ", source='" + source + '\'' +
            ", quotes=" + quotes +
            '}';
    }
}
