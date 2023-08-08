package shopping.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExchangeRateResponse {
    private final boolean success;
    private final String source;
    private final Map<String, Long> exchangeRates;

    @JsonCreator
    public ExchangeRateResponse(@JsonProperty("success") boolean success,
                                @JsonProperty("source") String source,
                                @JsonProperty("quotes") Map<String, Long> exchangeRates) {
        this.success = success;
        this.source = source;
        this.exchangeRates = exchangeRates;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getSource() {
        return source;
    }

    public Map<String, Long> getExchangeRates() {
        return exchangeRates;
    }
}
