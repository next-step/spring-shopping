package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shopping.application.ExchangeRateProvider;
import shopping.domain.EmptyExchangeRate;
import shopping.domain.ExchangeCode;
import shopping.domain.ExchangeRate;
import shopping.dto.response.ExchangeResponse;
import shopping.exception.InfraException;

@SpringBootTest
@DisplayName("CurrentExchangeRateProvider 클래스")
class CurrentExchangeRateProviderTest {

    @Autowired
    private ExchangeRateProvider exchangeRateProvider;

    @MockBean
    private CustomRestTemplate customRestTemplate;

    @Nested
    @DisplayName("getExchange 메소드는")
    class getExchange_Method {

        @DisplayName("입력 quote 에 따른 환율 정보를 가져온다")
        @Test
        void getExchange() {
            // given
            ExchangeCode code = ExchangeCode.USDKRW;
            Double codeExchange = 1302.2;
            ExchangeResponse response = createExchangeResponse(code, codeExchange);

            given(customRestTemplate.getResult(anyString(), any())).willReturn(Optional.of(response));

            // when
            ExchangeRate exchange = exchangeRateProvider.getExchange(code);

            // then
            assertThat(exchange.getValue()).isEqualTo(codeExchange);
        }

        @DisplayName("캐싱된 환율 정보를 가져온다")
        @Test
        void getExchange_fromCache() {
            // given
            ExchangeCode code = ExchangeCode.USDKRW;
            Double codeExchange = 1302.2;
            ExchangeResponse response = createExchangeResponse(code, codeExchange);

            given(customRestTemplate.getResult(anyString(), any())).willReturn(Optional.of(response));

            // when
            ExchangeRate originalExchange = exchangeRateProvider.getExchange(code);
            ExchangeRate cachedExchange = exchangeRateProvider.getExchange(code);

            // then
            assertThat(originalExchange).isSameAs(cachedExchange);
        }

        @DisplayName("유효하지 않은 quote 를 입력하면 InfraException 을 던진다 ")
        @Test
        void throwInfraException_whenQuoteIsInvalid() {
            // given
            ExchangeCode invalidCode = null;
            Double codeExchange = 1302.2;
            ExchangeResponse response = createExchangeResponse(ExchangeCode.USDKRW, codeExchange);

            given(customRestTemplate.getResult(anyString(), any())).willReturn(Optional.of(response));

            // when & then
            assertThatThrownBy(() -> exchangeRateProvider.getExchange(invalidCode))
                .hasMessage("지원하지 않는 환율 코드입니다")
                .isInstanceOf(InfraException.class);
        }

        @DisplayName("외부 api 호출 중 에러가 발생하면 InfraException 을 던진다")
        @Test
        void throwInfraException_whenApiCallThrowError() {
            // given
            ExchangeCode code = ExchangeCode.USDKRW;

            given(customRestTemplate.getResult(anyString(), any())).willThrow(InfraException.class);

            // when & then
            assertThatThrownBy(() -> exchangeRateProvider.getExchange(code))
                .isInstanceOf(InfraException.class);
        }

        @DisplayName("외부 api 호출 결과가 null 이면 EmptyExchangeRates 를 반환한다")
        @Test
        void returnEmptyExchangeRates_whenApiCallReturnNull() {
            // given
            ExchangeCode code = ExchangeCode.USDKRW;

            given(customRestTemplate.getResult(anyString(), any())).willReturn(Optional.empty());

            // when
            ExchangeRate exchange = exchangeRateProvider.getExchange(code);

            // then
            assertThat(exchange).isInstanceOf(EmptyExchangeRate.class);
        }
    }

    private ExchangeResponse createExchangeResponse(ExchangeCode code, Double codeExchange) {
        Map<String, Double> exchangeMap = new HashMap<>();
        exchangeMap.put(code.name(), codeExchange);
        return new ExchangeResponse(exchangeMap);
    }
}