package shopping.exchange;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CurrencyLayerCurrencyExchangeParser implements CurrencyExchangeParser {

    private final ObjectMapper objectMapper;

    public CurrencyLayerCurrencyExchangeParser(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, Double> parseExchangeRateMap(final ResponseEntity<JsonNode> response) {
        if (response.getStatusCode() != HttpStatus.OK) {
            return Collections.emptyMap();
        }

        final JsonNode body = response.getBody();
        if (Objects.isNull(body)) {
            return Collections.emptyMap();
        }

        return objectMapper.convertValue(body.path("quotes"), new TypeReference<>() {
        });
    }
}
