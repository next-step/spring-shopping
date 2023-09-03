package shopping.mart.infra.exchange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shopping.mart.domain.CurrencyRate;
import shopping.mart.domain.CurrencyType;
import shopping.mart.domain.ExchangeRateProvider;
import shopping.mart.dto.currencylayer.CurrencyLayerResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ExchangeRateProviderImpl implements ExchangeRateProvider {

    private static final String HOST = "http://api.currencylayer.com";
    private static final String END_POINT = "/live";

    private final RestTemplate restTemplate;
    private final String accessKey;

    public ExchangeRateProviderImpl(
        final RestTemplate restTemplate,
        @Value("${currencylayer.access-key}") final String accessKey
    ) {
        this.restTemplate = restTemplate;
        this.accessKey = accessKey;
    }

    @Override
    public CurrencyRate getCurrencyRate(final CurrencyType source, final CurrencyType target) {
        ResponseEntity<CurrencyLayerResponse> response = restTemplate.getForEntity(
            HOST + END_POINT + getQueryParams(target),
            CurrencyLayerResponse.class
        );

        if (isInvalidResponse(response)) {
            return CurrencyRate.empty();
        }

        Double currencyRate = response.getBody().getCurrency(source.name() + target.name());

        return new CurrencyRate(currencyRate);
    }

    private String getQueryParams(final CurrencyType target) {
        Map<String, String> params = Map.of(
            "access_key", accessKey,
            "currencies", target.name()
        );

        List<String> queryParams = params.entrySet().stream()
            .map(param -> param.getKey() + "=" + param.getValue())
            .collect(Collectors.toList());

        return "?" + String.join("&", queryParams);
    }

    private boolean isInvalidResponse(final ResponseEntity<CurrencyLayerResponse> response) {
        return !response.getStatusCode().equals(HttpStatus.OK)
            || Objects.isNull(response.getBody());
    }
}
