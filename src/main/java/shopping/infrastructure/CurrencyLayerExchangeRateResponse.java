package shopping.infrastructure;

import java.util.Map;

public class CurrencyLayerExchangeRateResponse {
    private static final int RETRYABLE_CODE = 106;
    private Map<String, Double> quotes;
    private boolean success;

    private Integer code;

    public CurrencyLayerExchangeRateResponse() {
    }

    public CurrencyLayerExchangeRateResponse(Map<String, Double> quotes, boolean success, Integer code) {
        this.quotes = quotes;
        this.success = success;
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isRetryable() {
        return code == RETRYABLE_CODE;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }

    public Integer getCode() {
        return code;
    }
}
