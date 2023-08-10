package shopping.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Objects;
import shopping.exception.CurrencyException;

public class Currency {

    Map<String, Double> quotes;

    @JsonCreator
    public Currency(@JsonProperty("quotes") Map<String, Double> quotes) {
        validateQuote(quotes);
        this.quotes = quotes;
    }

    public boolean isValidQuote(String quote) {
        return quotes.containsKey(quote);
    }

    public double getByQuote(String quote) {
        if (!isValidQuote(quote)) {
            throw new CurrencyException("지원하지 않는 국가 코드입니다");
        }
        return quotes.get(quote);
    }

    private void validateQuote(Map<String, Double> quotes) {
        if (Objects.isNull(quotes) || quotes.size() == 0) {
            throw new CurrencyException("환율 정보가 초기화되지 않았습니다");
        }
    }
}
