package shopping.currency;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shopping.exception.CurrencyLayerException;

@Service
public class CurrencyManager {

    @Value("${shopping.currency.url}")
    private String url;

    @Value("${shopping.currency.accessKey}")
    private String accessKey;

    private final ApiService apiService;

    public CurrencyManager(ApiService apiService) {
        this.apiService = apiService;
    }

    public double getExchangeRate(String base, String target) {
        CurrencyLayerResponse response = apiService.getResult(url + accessKey,
            CurrencyLayerResponse.class);

        validateResponse(response);

        return Optional.ofNullable(response.getQuotes().getOrDefault(base + target, null))
            .orElseThrow(() -> new CurrencyLayerException(
                MessageFormat
                    .format("{0} 에 해당하는 {1} 환율 정보를 불러오지 못했습니다", base, target)
            ));
    }

    private static void validateResponse(CurrencyLayerResponse response) {
        if (Objects.isNull(response.getQuotes())) {
            throw new CurrencyLayerException("quotes 정보를 불러오지 못했습니다");
        }
    }
}
