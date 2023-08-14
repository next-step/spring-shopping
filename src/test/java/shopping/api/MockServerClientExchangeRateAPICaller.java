package shopping.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shopping.dto.request.ExchangeRate;
import shopping.exception.CurrencyApiFailException;

@Component
@Profile("test")
public class MockServerClientExchangeRateAPICaller implements ExchangeRateAPICaller {

    private static final String QUOTES = "quotes";
    private static final String USDKRW = "USDKRW";
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final WebClient webClient;

    @Value("${secret.currency.url}")
    private String API_BASE_URL;

    @Value("${secret.currency.access_key}")
    private String API_ACCESS_KEY;


    public MockServerClientExchangeRateAPICaller(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public ExchangeRate getExchangeRate() {
        try {
            JsonNode response = webClient.get()
                    .uri(API_BASE_URL + API_ACCESS_KEY)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            log.info("This is Testing Method");
            log.info(String.valueOf(response));
            return new ExchangeRate(extractExchangeRate(response));
        } catch (Exception e) {
            log.error("환율 API 통신 실패", e);
            throw new CurrencyApiFailException();
        }
    }

    private static double extractExchangeRate(JsonNode response) {
        return response
                .get(QUOTES)
                .get(USDKRW)
                .asDouble();
    }
}
