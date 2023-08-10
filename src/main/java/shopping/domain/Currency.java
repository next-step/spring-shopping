package shopping.domain;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import shopping.exception.CurrencyException;

public class Currency {

    EnumMap<ExchangeCode, Double> codeMap;

    public Currency(Map<String, Double> quotes) {
        validateQuote(quotes);
        this.codeMap = createExchangeCodeMap(quotes);
    }

    private void validateCode(ExchangeCode code) {
        if (Objects.isNull(code) || !codeMap.containsKey(code)) {
            throw new CurrencyException("지원하지 않는 환율 코드입니다");
        }
    }

    public double getByCode(ExchangeCode code) {
        validateCode(code);
        return codeMap.get(code);
    }

    private EnumMap<ExchangeCode, Double> createExchangeCodeMap(Map<String, Double> original) {
        EnumMap<ExchangeCode, Double> codeMap = new EnumMap<>(ExchangeCode.class);
        Arrays.stream(ExchangeCode.values())
            .forEach(code -> codeMap.put(code, original.getOrDefault(code.name(), null)));
        return codeMap;
    }

    private void validateQuote(Map<String, Double> quotes) {
        if (Objects.isNull(quotes) || quotes.size() == 0) {
            throw new CurrencyException("환율 정보가 초기화되지 않았습니다");
        }
    }
}
