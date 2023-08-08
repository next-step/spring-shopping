package shopping.order.infra.exchange;

import com.fasterxml.jackson.databind.JsonNode;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import shopping.order.app.domain.Exchange;
import shopping.order.app.spi.ExchangeVendor;

public class CurrencyLayerExchangeVendor implements ExchangeVendor {

    private static final Set<Integer> RETRY_CODES = Set.of(104, 106, 201, 202);
    private static final int MAXIMUM_RETRY_COUNT = 5;

    private final RestTemplate restTemplate;
    private final String url;
    private final String accessKey;

    public CurrencyLayerExchangeVendor(@Qualifier("currencyRestTemplate") RestTemplate restTemplate,
            @Value("${exchange.vendor.currency.url}") String url,
            @Value("${exchange.vendor.currency.secret-key}") String accessKey) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.accessKey = accessKey;
    }

    @Override
    public Exchange currentExchange() {
        return retryCurrentExchange(0, "");
    }

    private Exchange retryCurrentExchange(int count, String failCodes) {
        validRetryable(count, failCodes);
        JsonNode jsonNode = restTemplate.getForObject(url + accessKey, JsonNode.class);
        if (isSuccess(Objects.requireNonNull(jsonNode))) {
            return new Exchange(jsonNode.get("quotes").path("USDKRW").asDouble());
        }

        Integer failCode = jsonNode.get("error").path("code").asInt();
        if (isRetryCode(failCode)) {
            return retryCurrentExchange(count + 1, failCodes + ", " + failCode);
        }

        throw new IllegalStateException(
                MessageFormat.format("currency layer에서 exchange를 불러오는데 실패했습니다. fail codes \"{0}\"", failCode));
    }

    private void validRetryable(int count, String failCodes) {
        if (count > MAXIMUM_RETRY_COUNT) {
            throw new IllegalStateException(
                    MessageFormat.format("currency layer에서 exchange를 불러오는데 실패했습니다. fail codes \"{0}\"", failCodes));
        }
    }

    private boolean isSuccess(JsonNode jsonNode) {
        return jsonNode.path("success").asBoolean();
    }

    private boolean isRetryCode(Integer failCode) {
        return RETRY_CODES.contains(failCode);
    }

}
