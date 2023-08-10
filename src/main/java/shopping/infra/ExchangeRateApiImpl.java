package shopping.infra;

import static shopping.exception.ShoppingErrorType.INVALID_EXCHANGE_RATE;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shopping.exception.ShoppingException;

@Component
@Profile("prod")
public class ExchangeRateApiImpl implements ExchangeRateApi {

    private static final String USDKRW = "USDKRW";
    private static final String LIVE_CURRENCY_API_URL = "http://api.currencylayer.com/live?currency=" + USDKRW;
    private final String accessKey;
    private final RestTemplate restTemplate;

    protected ExchangeRateApiImpl(@Value("${api.access-key}") final String accessKey) {
        this.accessKey = accessKey;
        this.restTemplate = new RestTemplate();
    }

    public double getUSDtoKRWExchangeRate() {
        final JsonNode jsonNode = restTemplate
                .getForObject(LIVE_CURRENCY_API_URL + "&access_key=" + accessKey, JsonNode.class);
        if (jsonNode == null) {
            throw new ShoppingException(INVALID_EXCHANGE_RATE);
        }

        return jsonNode.path("quotes").path(USDKRW).asDouble(Double.POSITIVE_INFINITY);
    }
}
