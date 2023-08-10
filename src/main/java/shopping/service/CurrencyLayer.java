package shopping.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shopping.domain.CurrencyCountry;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@Service
public class CurrencyLayer implements CurrencyService {

    @Value("${currency.access-key}")
    private String CURRENCY_ACCESS_KEY;
    @Value("${currency.source-url}")
    private String CURRENCY_SOURCE_URL;

    private final RestTemplate restTemplate;

    public CurrencyLayer() {
        restTemplate = new RestTemplate();
    }

    @Override
    public Double callCurrency(CurrencyCountry currencyCountry) {
        URI uri = getUri(currencyCountry);
        JsonNode jsonNode = restTemplate.getForObject(uri, JsonNode.class);
        if (!jsonNode.get("success").booleanValue()) {
            throw new ShoppingException(ErrorCode.INVALID_CURRENCY_API);
        }
        return getCurrency(jsonNode, currencyCountry);
    }

    private Double getCurrency(JsonNode jsonNode, CurrencyCountry currencyCountry) {
        return jsonNode
            .get("quotes")
            .get(currencyCountry.getSourceTargetCountry())
            .asDouble();
    }

    private URI getUri(CurrencyCountry currencyCountry) {
        return UriComponentsBuilder.fromHttpUrl(CURRENCY_SOURCE_URL)
            .queryParam("access_key", CURRENCY_ACCESS_KEY)
            .queryParam("currencies", currencyCountry.getTargetCurrency())
            .queryParam("source", currencyCountry.getSourceCurrency())
            .build()
            .toUri();
    }

}
