package shopping.dto.response;

import java.util.Map;

public class ExchangeRateResponse {
    private boolean success;
    private String source;
    private Map<String, Long> quotes;

    public boolean getSuccess() {
        return success;
    }

    public String getSource() {
        return source;
    }

    public Map<String, Long> getQuotes() {
        return quotes;
    }
}
