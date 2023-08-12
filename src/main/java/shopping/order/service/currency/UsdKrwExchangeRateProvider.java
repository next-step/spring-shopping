package shopping.order.service.currency;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shopping.exception.WooWaException;
import shopping.order.domain.vo.ExchangeRate;

@Component
@Profile("!test")
public class UsdKrwExchangeRateProvider implements ExchangeRateProvider {

    private static final String ACCESS_KEY_PARAM = "access_key";
    private static final String QUOTES_FIELD = "quotes";
    private static final String USDKRW_FIELD = "USDKRW";
    private final String url;
    private final String accessKey;
    private final RestTemplate restTemplate;

    public UsdKrwExchangeRateProvider(
        @Value("${currency.api.url}") String url,
        @Value("${currency.api.key}") String accessKey,
        RestTemplate restTemplate) {
        this.url = url;
        this.accessKey = accessKey;
        this.restTemplate = restTemplate;
    }

    @Override
    public ExchangeRate findTargetExchangeRate() {
        ResponseEntity<JsonNode> response = findHttpResponse();
        validResponseHttpStatus(response);
        return new ExchangeRate(parseResponseBodyUsdKrw(response));
    }

    private ResponseEntity<JsonNode> findHttpResponse() {
        try {
            return restTemplate.getForEntity(makeUrl(), JsonNode.class);
        } catch (RestClientException e) {
            throw new WooWaException("외부 요청에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validResponseHttpStatus(ResponseEntity<JsonNode> response) {
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new WooWaException("외부 요청에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String parseResponseBodyUsdKrw(ResponseEntity<JsonNode> response) {
        return response.getBody().path(QUOTES_FIELD).get(USDKRW_FIELD).asText();
    }

    private URI makeUrl() {
        return UriComponentsBuilder.fromHttpUrl(url)
            .queryParam(ACCESS_KEY_PARAM, accessKey)
            .build()
            .toUri();
    }
}
