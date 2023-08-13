package shopping.exchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(CurrencyLayerClient.class)
class CurrencyLayerClientTest {

    @Autowired
    CurrencyLayerClient client;

    @Autowired
    MockRestServiceServer server;

    @Autowired
    ObjectMapper objectMapper;

    static String BASE_URL, ACCESS_KEY;

    @BeforeAll
    static void beforeAll(
        @Value("${currency-layer.base-url}") final String baseUrl,
        @Value("${currency-layer.access-key}") final String accessKey
    ) {
        BASE_URL = baseUrl;
        ACCESS_KEY = accessKey;
    }

    @Test
    @DisplayName("환율 정보를 받아온다.")
    void sendLiveCurrencyExchangeRateRequest() {
        /* given */
        final String json = "{\n"
            + "    \"success\": true,\n"
            + "    \"terms\": \"https://currencylayer.com/terms\",\n"
            + "    \"privacy\": \"https://currencylayer.com/privacy\",\n"
            + "    \"timestamp\": 1430401802,\n"
            + "    \"source\": \"USD\",\n"
            + "    \"quotes\": {\n"
            + "        \"USDAED\": 3.672982,\n"
            + "        \"USDAFN\": 57.8936,\n"
            + "        \"USDALL\": 126.1652,\n"
            + "        \"USDANG\": 1.78952,\n"
            + "        \"USDAOA\": 109.216875,\n"
            + "        \"USDARS\": 8.901966,\n"
            + "        \"USDAUD\": 1.269072,\n"
            + "        \"USDAWG\": 1.792375,\n"
            + "        \"USDAZN\": 1.04945,\n"
            + "        \"USDBAM\": 1.757305\n"
            + "    }\n"
            + "}";
        server.expect(ExpectedCount.manyTimes(),
                requestTo(BASE_URL + "/live?access_key=" + ACCESS_KEY))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        /* when */
        final ResponseEntity<JsonNode> response = client.sendLiveCurrencyExchangeRateRequest();

        /* then */
        assertThat(response.getBody().path("success").asBoolean()).isTrue();
    }
}
