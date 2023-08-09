package shopping.currency;

import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shopping.exception.WooWaException;

@Component
public class CurrencyProviderImpl implements CurrencyProvider {

    private static final String URL = "http://apilayer.net/api/live";
    @Value("${currency.api.key}")
    private String accessKey;
    private final RestTemplate restTemplate;


    public CurrencyProviderImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal findUsdKrwCurrency() {
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(makeUrl(), JsonNode.class);
        validResponseHttpStatus(response);
        return new BigDecimal(parseResponseBodyUsdKrw(response));
    }

    private void validResponseHttpStatus(ResponseEntity<JsonNode> response) {
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new WooWaException("외부 요청에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String parseResponseBodyUsdKrw(ResponseEntity<JsonNode> response) {
        return response.getBody().path("quotes").get("USDKRW").asText();
    }

    private URI makeUrl() {
        return UriComponentsBuilder.fromHttpUrl(URL)
            .queryParam("access_key", accessKey)
            .build()
            .toUri();
    }
}
