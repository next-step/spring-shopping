package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.application.ExchangeRateProvider;
import shopping.domain.ExchangeCode;
import shopping.domain.ExchangeRate;
import shopping.dto.response.ExchangeResponse;
import shopping.exception.InfraException;

@ExtendWith(MockitoExtension.class)
@DisplayName("CurrentExchangeRateProvider 클래스")
class CurrentExchangeRateProviderTest {

    private ExchangeRateProvider exchangeRateProvider;
    private CustomRestTemplate customRestTemplate;

    @BeforeEach
    void setUp() {
        customRestTemplate = Mockito.mock(CustomRestTemplate.class);
        exchangeRateProvider = new CurrentExchangeRateProvider("apiKey", customRestTemplate);
    }

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

            given(customRestTemplate.getResult(anyString(), any())).willReturn(response);

            // when
            ExchangeRate exchange = exchangeRateProvider.getExchange(code);

            // then
            assertThat(exchange.getValue()).isEqualTo(codeExchange);
        }

        @DisplayName("유효하지 않은 quote 를 입력하면 InfraException 을 던진다 ")
        @Test
        void throwInfraException_whenQuoteIsInvalid() {
            // given
            ExchangeCode invalidCode = null;
            Double codeExchange = 1302.2;
            ExchangeResponse response = createExchangeResponse(ExchangeCode.USDKRW, codeExchange);

            given(customRestTemplate.getResult(anyString(), any())).willReturn(response);

            // when & then
            assertThatThrownBy(() -> exchangeRateProvider.getExchange(invalidCode))
                .hasMessage("지원하지 않는 환율 코드입니다")
                .isInstanceOf(InfraException.class);
        }
    }

    private ExchangeResponse createExchangeResponse(ExchangeCode code, Double codeExchange) {
        Map<String, Double> exchangeMap = new HashMap<>();
        exchangeMap.put(code.name(), codeExchange);
        return new ExchangeResponse(exchangeMap);
    }
}