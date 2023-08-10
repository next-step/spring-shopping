package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.domain.Currency;
import shopping.exception.InfraException;

@DisplayName("CucstomRestTemplate 단위 테스트")
class CustomRestTemplateTest {

    CustomRestTemplate customRestTemplate = new CustomRestTemplate();

    @DisplayName("getResult 메서드는")
    @Nested
    class getResult_Method {

        @DisplayName("유효하지 않은 주소로 요청을 보내면 InfraException 을 던진다")
        @Test
        void throwInfraException_WhenInvalidUrlRequest() {
            // given
            String invalidUrl = "http://url./tesafdfas/ds/afasdf/asf/as/fs//adsfs/af/asd";

            // when & then
            assertThatThrownBy(() -> customRestTemplate.getResult(invalidUrl, Currency.class))
                .hasMessage("외부 API 호출 중 에러가 발생했습니다")
                .isInstanceOf(InfraException.class);
        }
    }
}