package shopping.exchange;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class CurrencyLayerParserTest {

    CurrencyLayerParser currencyLayerParser;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        currencyLayerParser = new CurrencyLayerParser(objectMapper);
    }

    @Test
    @DisplayName("Currency layer API 응답에서 환율 정보를 반환한다.")
    void parseExchangeRate() {
        /* given */
        final JsonNode quotes = objectMapper.createObjectNode()
            .put("USDKRW", 1234.5);

        final JsonNode body = objectMapper.createObjectNode()
            .put("success", true)
            .set("quotes", quotes);

        final ResponseEntity<JsonNode> response =
            new ResponseEntity<>(body, HttpStatus.OK);

        /* when */
        final Map<String, Double> exchangeRateByCurrency =
            currencyLayerParser.parseExchangeRateMap(response);

        /* then */
        assertThat(exchangeRateByCurrency).containsEntry("USDKRW", 1234.5);
    }

    @Test
    @DisplayName("Currency layer API 응답이 200 OK가 아닌 경우 비어 있는 환율 정보를 반환한다.")
    void paresExchangeRateFailWithNotOk() {
        /* given */
        final JsonNode body = objectMapper.createObjectNode()
            .put("success", false);

        final ResponseEntity<JsonNode> response =
            new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

        /* when */
        final Map<String, Double> exchangeRateByCurrency =
            currencyLayerParser.parseExchangeRateMap(response);

        /* then */
        assertThat(exchangeRateByCurrency).doesNotContainKey("USDKRW");
    }

}
