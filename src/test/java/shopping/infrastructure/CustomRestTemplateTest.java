package shopping.infrastructure;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import shopping.domain.ExchangeCode;
import shopping.domain.ExchangeRates;
import shopping.exception.InfraException;

@DisplayName("CustomRestTemplate 단위 테스트")
class CustomRestTemplateTest {

    private RestTemplate restTemplate;
    private CustomRestTemplate customRestTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        customRestTemplate = new CustomRestTemplate(restTemplate);
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
            ResponseEntity<ExchangeRates> response = createApiResponse(code);

            given(restTemplate.getForEntity(url, ExchangeRates.class)).willReturn(response);

            // when
            ExchangeRates result = customRestTemplate.getResult(url, ExchangeRates.class);

            // then
            assertThat(result.getRate(code)).isEqualTo(response.getBody().getRate(code));
        }

        @DisplayName("외부 api 호출의 응답 값이 2xx 가 아니면 InfraException 을 던진다")
        @Test
        void throwInfraException_WhenStatusCodeIsNot200() {
            // given
            String url = "https://url";
            ResponseEntity<ExchangeRates> response = ResponseEntity.internalServerError().build();

            given(restTemplate.getForEntity(url, ExchangeRates.class)).willReturn(response);

            // when & then
            assertThatThrownBy(() -> customRestTemplate.getResult(url, ExchangeRates.class))
                .hasMessage("외부 API 호출 중 에러가 발생했습니다")
                .isInstanceOf(InfraException.class);
        }

        @DisplayName("유효하지 않은 주소로 요청을 보내면 InfraException 을 던진다")
        @Test
        void throwInfraException_WhenInvalidUrlRequest() {
            // given
            String invalidUrl = "http://url./tesafdfas/ds/afasdf/asf/as/fs//adsfs/af/asd";

            given(restTemplate.getForEntity(invalidUrl, ExchangeRates.class)).willThrow(RestClientException.class);

            // when & then
            assertThatThrownBy(() -> customRestTemplate.getResult(invalidUrl, ExchangeRates.class))
                .hasMessage("외부 API 호출 중 에러가 발생했습니다")
                .isInstanceOf(InfraException.class);
        }
    }

    private ResponseEntity<ExchangeRates> createApiResponse(ExchangeCode code) {
        Map<String, Double> exchangeMap = new HashMap<>();
        exchangeMap.put(code.name(), 1202.1);
        ExchangeRates exchangeRates = new ExchangeRates(exchangeMap);
        return ResponseEntity.ok().body(exchangeRates);
    }
}