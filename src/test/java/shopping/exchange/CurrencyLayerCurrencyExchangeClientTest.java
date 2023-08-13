package shopping.exchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest
class CurrencyLayerCurrencyExchangeClientTest {

    @Value("${currency-layer.base-url}")
    String baseUrl;
    @Value("${currency-layer.access-key}")
    String accessKey;

    @Autowired
    MockRestServiceServer server;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Test
    @DisplayName("환율 정보를 받아온다.")
    void sendLiveCurrencyExchangeRateRequest() {
        /* given */
        final CurrencyLayerCurrencyExchangeClient client =
            new CurrencyLayerCurrencyExchangeClient(baseUrl, accessKey, restTemplateBuilder);

        server.expect(ExpectedCount.manyTimes(),
                requestTo(baseUrl + "/live?access_key=" + accessKey))
            .andRespond(withSuccess(
                "{\n"
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
                    + "}", MediaType.APPLICATION_JSON));

        /* when */
        final ResponseEntity<JsonNode> response = client.sendLiveCurrencyExchangeRateRequest();

        /* then */
        assertThat(response.getBody().path("success").asBoolean()).isTrue();
    }

    @Disabled("실제 API 응답 결과가 궁금할 때만 사용한다.")
    @Test
    @DisplayName("CurrencyLayer에서 환율 정보를 가져온다.")
    void actualCurrencyLayerApi() {
        /* given */
        final CurrencyLayerCurrencyExchangeClient client =
            new CurrencyLayerCurrencyExchangeClient(baseUrl, accessKey, new RestTemplateBuilder());

        /* when */
        final ResponseEntity<JsonNode> response = client.sendLiveCurrencyExchangeRateRequest();

        /* then */
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        System.out.println(response.getBody().toPrettyString());
    }
}
