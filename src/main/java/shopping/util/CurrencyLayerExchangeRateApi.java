package shopping.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shopping.global.exception.ShoppingException;

@Component
@Profile("!test")
public class CurrencyLayerExchangeRateApi implements ExchangeRateApi {

    private static final String targetCountry = "KRW";
    private static final String sourceCountry = "USD";
    private static final String baseurl = "/live";
    private final String url;

    private final RestTemplate restTemplate;
    private final String accessKey;

    public CurrencyLayerExchangeRateApi(
        final @Value("${currency.accessKey}") String accessKey,
        final @Value("${currency.baseUrl}") String baseurl,
        final RestTemplate restTemplate
    ) {
        this.accessKey = accessKey;
        this.url = baseurl + this.baseurl
            + "?currencies=" + targetCountry
            + "&access_key=" + accessKey;
        this.restTemplate = restTemplate;
    }

    @Override
    public double callExchangeRate() {
        JsonNode quotes = getJsonNode()
            .get(sourceCountry + targetCountry);
        validateExchangeRate(quotes);
        return quotes.asDouble();
    }

    private JsonNode getJsonNode() {
        JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class).get("quotes");
        validateJsonNode(jsonNode);
        return jsonNode;
    }

    private void validateJsonNode(final JsonNode jsonNode) {
        if (jsonNode == null) {
            throw new ShoppingException("ExchangeAPI json가 존재하지 않습니다.");
        }
    }

    private void validateExchangeRate(final JsonNode quotes) {
        if (quotes.isNull()) {
            throw new ShoppingException("ExchangeAPI에서 exchangeRate가 존재하지 않습니다");
        }
    }
}
