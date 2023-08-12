package shopping.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shopping.global.exception.ShoppingException;
import shopping.infrastructure.dto.ExchangeRateResponse;
import shopping.order.domain.vo.ExchangeRate;

@Component
@Profile("!test")
public class CurrencyLayerExchangeRateApi implements ExchangeRateApi {

    private static final String targetCountry = "KRW";
    private static final String sourceCountry = "USD";
    private static final String baseurl = "/live";
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRate.class);
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
    public ExchangeRateResponse callExchangeRate() {
        try {
            JsonNode quotes = getJsonNode()
                .get(sourceCountry + targetCountry);
            validateExchangeRate(quotes);
            return new ExchangeRateResponse(quotes.asDouble());
        } catch (ShoppingException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private JsonNode getJsonNode() {
        JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class);
        validateJsonNode(jsonNode);
        validateJsonNode(jsonNode.get("quotes"));
        return jsonNode.get("quotes");
    }

    private void validateJsonNode(final JsonNode jsonNode) {
        if (jsonNode == null) {
            throw new ShoppingException("ExchangeAPI json가 존재하지 않습니다.");
        }
    }

    private void validateExchangeRate(final JsonNode quotes) {
        if (quotes == null) {
            throw new ShoppingException("ExchangeAPI에서 exchangeRate가 존재하지 않습니다");
        }
    }
}
