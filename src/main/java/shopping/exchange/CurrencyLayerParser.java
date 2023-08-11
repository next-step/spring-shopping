package shopping.exchange;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrencyLayerParser {

    private final ObjectMapper objectMapper;

    public CurrencyLayerParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, Double> parseExchangeRateMap(final ResponseEntity<JsonNode> response) {
        if (response.getStatusCode() != HttpStatus.OK) {
            return Collections.emptyMap();
        }

        final JsonNode quotes = response.getBody().path("quotes");

        return objectMapper.convertValue(quotes, new TypeReference<>() {
        });
    }
}
