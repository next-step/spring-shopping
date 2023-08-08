package shopping.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shopping.exception.CurrencyApiFailException;

@Component
public class CurrencyAPICaller implements CurrencyCaller {

    private static final String QUOTES = "quotes";
    private static final String USDKRW = "USDKRW";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final WebClient webClient;

    public CurrencyAPICaller(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Double getCurrency() {
        try {
            JsonNode response = webClient.get()
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            return extractCurrency(response);
        } catch (Exception e) {
            log.error("환율 API 통신 실패", e);
            throw new CurrencyApiFailException();
        }
    }

    private static double extractCurrency(JsonNode response) {
        return response
                .get(QUOTES)
                .get(USDKRW)
                .asDouble();
    }
}
