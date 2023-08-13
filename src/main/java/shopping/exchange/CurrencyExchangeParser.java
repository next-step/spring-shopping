package shopping.exchange;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface CurrencyExchangeParser {

    Map<String, Double> parseExchangeRateMap(final ResponseEntity<JsonNode> response);

}
