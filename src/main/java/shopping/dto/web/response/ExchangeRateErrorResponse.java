package shopping.dto.web.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRateErrorResponse {

    private final int code;
    private final String information;

    @JsonCreator
    public ExchangeRateErrorResponse(@JsonProperty("code") int code, @JsonProperty("info") String information) {
        this.code = code;
        this.information = information;
    }

    public int getCode() {
        return code;
    }

    public String getInformation() {
        return information;
    }
}
