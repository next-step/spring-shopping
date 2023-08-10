package shopping.dto.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import shopping.dto.web.response.ExchangeRateErrorResponse;

import java.util.Map;

public class ExchangeRateResponse {

    private final boolean success;
    private final String source;
    private final Map<String, Double> exchangeRates;
    private final ExchangeRateErrorResponse error;

    @JsonCreator
    public ExchangeRateResponse(@JsonProperty("success") boolean success,
                                @JsonProperty("source") String source,
                                @JsonProperty("quotes") Map<String, Double> exchangeRates,
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

    public Map<String, Double> getExchangeRates() {
        return exchangeRates;
    }

    public ExchangeRateErrorResponse getError() {
        return error;
    }
}
