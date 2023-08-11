package shopping.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;
import shopping.aspect.annotation.Retry;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class USDExchangeRateApi implements ExchangeRateApi {

    private static final String QUOTES = "quotes";
    private static final String USD_KRW = "USDKRW";
    private static final String ACCESS_KEY = "access_key";
    private static final long MINUTES = 1L;
    private final WebClient webClient;
    private final String currencyApiUrl;
    private final String accessKey;

    private final CachedExchangeRate cachedExchangeRate;

    public USDExchangeRateApi(
            final WebClient webClient,
            @Value("${currency-api.key}") final String accessKey,
            @Value("${currency-api.url}") final String currencyApiUrl
    ) {
        this.webClient = webClient;
        this.accessKey = accessKey;
        this.currencyApiUrl = currencyApiUrl;
        this.cachedExchangeRate = new CachedExchangeRate(MINUTES);
    }

    @Retry
    @Override
    public double getExchangeRateEveryMinute(final LocalDateTime now) {
        if (cachedExchangeRate.isLatest(now)) {
            return cachedExchangeRate.getExchangeRate(now);
        }
        final JsonNode response = requestCurrencyApi();

        return getExchangeRage(response, now);
    }


    private JsonNode requestCurrencyApi() {
        try {
            return webClient.get()
                    .uri(uriBuild())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
        } catch (WebClientException e) {
            throw new ShoppingException(e, ErrorCode.CURRENCY_API_ERROR);
        }
    }

    private double getExchangeRage(final JsonNode response, final LocalDateTime now) {
        final double exchangeRage = getUSDKRW(response).asDouble();
        cachedExchangeRate.updateExchangeRate(exchangeRage, now);

        return exchangeRage;
    }

    private JsonNode getUSDKRW(final JsonNode body) {
        if (Objects.isNull(body)) {
            throw new ShoppingException(ErrorCode.CURRENCY_API_ERROR);
        }

        return body.path(QUOTES).get(USD_KRW);

    }

    private URI uriBuild() {
        return UriComponentsBuilder.fromHttpUrl(currencyApiUrl)
                .queryParam(ACCESS_KEY, accessKey)
                .build()
                .toUri();
    }
}
