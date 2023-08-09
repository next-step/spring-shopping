package shopping.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExchangeRateResponse {

    private final boolean success;
    private final String source;
    private final Map<String, Long> exchangeRates;
    private final ExchangeRateErrorResponse error;

    @JsonCreator
    public ExchangeRateResponse(@JsonProperty("success") boolean success,
                                @JsonProperty("source") String source,
                                @JsonProperty("quotes") Map<String, Long> exchangeRates,
                                @JsonProperty("error") ExchangeRateErrorResponse error) {
        this.success = success;
        this.source = source;
        this.exchangeRates = exchangeRates;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSource() {
        return source;
    }

    public Map<String, Long> getExchangeRates() {
        return exchangeRates;
    }

    public ExchangeRateErrorResponse getError() {
        return error;
    }
}
