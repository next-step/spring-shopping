package shopping.exchange;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

public interface CurrencyExchangeClient {

    ResponseEntity<JsonNode> sendLiveCurrencyExchangeRateRequest();

}
