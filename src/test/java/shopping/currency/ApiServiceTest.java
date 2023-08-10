package shopping.currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

@ExtendWith(SpringExtension.class)
@RestClientTest(ApiService.class)
class ApiServiceTest {

    @Autowired
    private ApiService apiService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Value("${shopping.currency.url}")
    private String url;

    @Value("${shopping.currency.accessKey}")
    private String accessKey;

    @Nested
    @DisplayName("getResult 메서드는")
    class GetResult_Method {

        @Test
        @DisplayName("외부 api 에 요청하여 응답을 받는다")
        void getResponseWithExternalApi() {
            // given
            String expectedJsonResponse = "{\"quotes\": {\"USDKRW\": 1300.672982,\"USDAFN\": 57.8936}}";

            mockServer
                .expect(requestTo(url + accessKey))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedJsonResponse, MediaType.APPLICATION_JSON));

            // when
            CurrencyLayerResponse response = apiService.getResult(url + accessKey, CurrencyLayerResponse.class);

            // then
            assertThat(response).isNotNull();
            assertThat(response.getQuotes()).isNotNull();

            mockServer.verify();
        }
    }

}