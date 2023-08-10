package shopping.currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.dto.CurrencyLayerResponse;
import shopping.exception.CurrencyLayerException;
import shopping.infrastructure.ApiService;
import shopping.infrastructure.CurrencyManager;

@DisplayName("CurrencyManager 클래스")
@ExtendWith(MockitoExtension.class)
public class CurrencyManagerTest {

    @InjectMocks
    private CurrencyManager currencyManager;

    @Mock
    private ApiService apiService;

    @Nested
    @DisplayName("getExchangeRate 메서드는")
    class GetExchangeRate_Method {

        @Test
        @DisplayName("base 에 해당하는 target 의 환율을 반환한다")
        void returnExchangeRate() {
            // given
            String base = "USD";
            String target = "KRW";
            double exchangeRate = 1300.0;

            Map<String, Double> quotes = new HashMap<>();
            quotes.put(base + target, exchangeRate);

            when(apiService.getResult(anyString(), eq(CurrencyLayerResponse.class)))
                .thenReturn(new CurrencyLayerResponse(quotes));

            // when
            double result = currencyManager.getExchangeRate(base, target);

            // then
            assertThat(result).isEqualTo(exchangeRate);
        }

        @Test
        @DisplayName("quotes 가 존재하지 않을 경우 CurrencyLayerException 을 던진다")
        void throwCurrencyLayerException_WhenQuotesIsNull() {
            // given
            String base = "USD";
            String target = "KRW";

            when(apiService.getResult(anyString(), eq(CurrencyLayerResponse.class)))
                .thenReturn(new CurrencyLayerResponse(null));

            // when
            Exception exception = catchException(
                () -> currencyManager.getExchangeRate(base, target));

            // then
            assertThat(exception).isInstanceOf(CurrencyLayerException.class);
            assertThat(exception.getMessage()).contains("quotes 정보를 불러오지 못했습니다");
        }

        @Test
        @DisplayName("base 에 해당하는 target 환율 정보가 없을 경우 CurrencyLayerException 을 던진다")
        void throwCurrencyLayerException_WhenRateNull() {
            // given
            String base = "USD";
            String target = "KRW";

            when(apiService.getResult(anyString(), eq(CurrencyLayerResponse.class)))
                .thenReturn(new CurrencyLayerResponse(new HashMap<>()));

            // when
            Exception exception = catchException(
                () -> currencyManager.getExchangeRate(base, target));

            // then
            assertThat(exception).isInstanceOf(CurrencyLayerException.class);
            assertThat(exception.getMessage()).contains("환율 정보를 불러오지 못했습니다");
        }

    }
}
