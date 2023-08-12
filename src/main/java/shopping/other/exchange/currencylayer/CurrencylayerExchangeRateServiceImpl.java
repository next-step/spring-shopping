package shopping.other.exchange.currencylayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import shopping.common.domain.Rate;
import shopping.order.service.ExchangeRateService;
import shopping.order.service.ExchangeType;


public class CurrencylayerExchangeRateServiceImpl implements ExchangeRateService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final int JSON_FORMAT_ON = 1;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String apikey;
    private final String exchangeRateUrl;

    public CurrencylayerExchangeRateServiceImpl(WebClient webClient, ObjectMapper objectMapper,
        String apikey, String exchangeRateUrl) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.apikey = apikey;
        this.exchangeRateUrl = exchangeRateUrl;
    }

    @Override
    public Rate getExchangeRate(ExchangeType source, ExchangeType target) {
        return parseJson(source, target, getExchangeRateJson(source, target));
    }

    private Rate parseJson(ExchangeType source, ExchangeType target, String jsonString) {
        JsonNode root = null;
        try {
            root = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            log.error("외부 서비스 문제 발생");
            return null;
        }
        return Rate.valueOf(root.path("quotes")
            .path(source.name() + target.name()).asDouble());
    }

    public String getExchangeRateJson(ExchangeType source, ExchangeType target) {

        return webClient.get()
            .uri(uriBuild(source, target))
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    private URI uriBuild(ExchangeType source, ExchangeType target) {
        return UriComponentsBuilder.fromHttpUrl(exchangeRateUrl)
            .queryParam("access_key", apikey)
            .queryParam("currencies", target.name())
            .queryParam("source", source.name())
            .queryParam("format", JSON_FORMAT_ON)
            .build()
            .toUri();
    }

}
