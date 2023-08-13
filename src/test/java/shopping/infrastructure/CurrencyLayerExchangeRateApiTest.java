package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;
import shopping.config.TestConfig;
import shopping.infrastructure.dto.ExchangeRateResponse;

@RestClientTest(CurrencyLayerExchangeRateApi.class)
@Import(TestConfig.class)
public class CurrencyLayerExchangeRateApiTest {

    private @Value("${currency.accessKey}") String accessKey;
    private @Value("${currency.baseUrl}") String baseurl;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyLayerExchangeRateApi exchangeRateApi;
    private MockRestServiceServer mockServer;


    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @DisplayName("정상 동작 확인")
    void 정상_동작_확인() {
        // given
        String jsonResponse = "{\"quotes\": {\"USD" + "KRW\": 1200}}";
        mockServer
            .expect(requestTo(baseurl + "/live?currencies=KRW&access_key=" + accessKey))
            .andRespond(
                MockRestResponseCreators.withSuccess(jsonResponse, MediaType.APPLICATION_JSON)
            );
        // when
        ExchangeRateResponse response = exchangeRateApi.callExchangeRate();
        // then
        assertThat(response.getRate()).isEqualTo(1200);
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"hello\": {\"USD" + "KRW\": 1200}}", "{}"})
    @DisplayName("만약 response가 quotes 라는 요소가 없다면, null을 반환한다.")
    void response_있을때_quotes가_없을때_null(String jsonResponse) {
        // given
        mockServer
            .expect(requestTo(baseurl + "/live?currencies=KRW&access_key=" + accessKey))
            .andRespond(
                MockRestResponseCreators.withSuccess(jsonResponse, MediaType.APPLICATION_JSON)
            );
        // when
        ExchangeRateResponse response = exchangeRateApi.callExchangeRate();

        // then
        assertThat(response).isEqualTo(null);
    }

    @Test
    @DisplayName("만약 response에 qutoes가 있지만, KRW 가 없을 때 null을 반환한다.")
    public void response_있을때_quotes가_있지만_KRW가_없을때_null() {
        // given
        String jsonResponse = "{\"hello\": {\"USD" + "EUR\": 1200}}";
        mockServer
            .expect(requestTo(baseurl + "/live?currencies=KRW&access_key=" + accessKey))
            .andRespond(
                MockRestResponseCreators.withSuccess(jsonResponse, MediaType.APPLICATION_JSON));
        // when
        ExchangeRateResponse response = exchangeRateApi.callExchangeRate();

        // then
        assertThat(response).isEqualTo(null);
    }
}
