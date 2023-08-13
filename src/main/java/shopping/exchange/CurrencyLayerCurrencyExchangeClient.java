package shopping.exchange;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CurrencyLayerCurrencyExchangeClient implements CurrencyExchangeClient {

    private static final String LIVE_EXCHANGE_RATE_PATH = "/live?access_key=";

    private final String baseUrl;
    private final String accessKey;
    private final RestTemplate restTemplate;

    public CurrencyLayerCurrencyExchangeClient(
        final String baseUrl,
        final String accessKey,
        final RestTemplateBuilder restTemplateBuilder
    ) {
        this.baseUrl = baseUrl;
        this.accessKey = accessKey;
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<JsonNode> sendLiveCurrencyExchangeRateRequest() {
        return restTemplate
            .getForEntity(baseUrl + LIVE_EXCHANGE_RATE_PATH + accessKey, JsonNode.class);
    }
}
