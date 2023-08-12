package shopping.infrastructure;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import shopping.domain.ExchangeCode;
import shopping.dto.response.ExchangeResponse;
import shopping.exception.InfraException;

@DisplayName("CustomRestTemplate 단위 테스트")
class CustomRestTemplateTest {

    private RestTemplate restTemplate;
    private CustomRestTemplate customRestTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        customRestTemplate = new CustomRestTemplate(restTemplate, Mockito.mock(ExceptionLogger.class));
    }

    @DisplayName("getResult 메서드는")
    @Nested
    class getResult_Method {

        @DisplayName("외부 api 응답으로부터 환율 정보를 생성한다")
        @Test
        void getResult() {
            // given
            String url = "https://url";
            ExchangeCode code = ExchangeCode.USDKRW;
            ExchangeResponse response = createApiResponse(code);

            given(restTemplate.getForObject(url, ExchangeResponse.class)).willReturn(response);

            // when
            Optional<ExchangeResponse> result = customRestTemplate.getResult(url, ExchangeResponse.class);

            // then
            assertThat(result)
                .isPresent()
                .hasValue(response);
        }

        @DisplayName("외부 api 가 응답으로 null 을 반환하면 Optional.empty 를 반환한다")
        @Test
        void returnEmpty_WhenApiReturnNull() {
            // given
            String url = "https://url";

            given(restTemplate.getForObject(url, ExchangeResponse.class)).willReturn(null);

            // when & then
            assertThat(customRestTemplate.getResult(url, ExchangeResponse.class)).isEmpty();
        }

        @DisplayName("api 호출 중 에러가 발생하면 Optional.empty 를 반환한다")
        @Test
        void throwInfraException_WhenInvalidUrlRequest() {
            // given
            String invalidUrl = "http://url./tesafdfas/ds/afasdf/asf/as/fs//adsfs/af/asd";

            given(restTemplate.getForObject(invalidUrl, ExchangeResponse.class)).willReturn(null);

            // when & then
            assertThat(customRestTemplate.getResult(invalidUrl, ExchangeResponse.class)).isEmpty();
        }
    }

    private ExchangeResponse createApiResponse(ExchangeCode code) {
        Map<String, Double> exchangeMap = new HashMap<>();
        exchangeMap.put(code.name(), 1202.1);
        return new ExchangeResponse(exchangeMap);
    }
}