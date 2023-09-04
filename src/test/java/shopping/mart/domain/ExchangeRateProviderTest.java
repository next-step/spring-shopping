package shopping.mart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import shopping.mart.dto.currencylayer.CurrencyLayerResponse;
import shopping.mart.infra.exchange.ExchangeRateProviderImpl;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("ExchangeRateProvider 클래스")
@ContextConfiguration(classes = {ExchangeRateProviderImpl.class})
class ExchangeRateProviderTest {

    @Autowired
    private ExchangeRateProvider exchangeRateProvider;

    @MockBean
    private RestTemplate restTemplate;

    @Nested
    @DisplayName("getCurrencyRate 메서드는")
    class getCurrencyRate_method {

        @Test
        @DisplayName("source, target을 사용해 api.currencylayer.com로부터 환율을 조회한다.")
        void return_currency_rate_by_source_and_target() {
            // given
            CurrencyLayerResponse body = new CurrencyLayerResponse(true, 12345678L, "USD",
                    Map.of("USDKRW", 1234.56));
            when(restTemplate.getForEntity(anyString(), any()))
                    .thenReturn(new ResponseEntity<>(body, HttpStatus.OK));

            CurrencyType source = CurrencyType.USD;
            CurrencyType target = CurrencyType.KRW;

            // when
            CurrencyRate currencyRate = exchangeRateProvider.getCurrencyRate(source, target);

            // then
            assertThat(currencyRate.getValue()).isEqualTo(1234.56);
        }

        @Test
        @DisplayName("api.currencylayer.com로부터 환율 조회를 실패했을 경우 null이 저장된 CurrencyRate를 반환한다.")
        void return_null_currency_rate_when_fail_request() {
            // given
            when(restTemplate.getForEntity(anyString(), any()))
                    .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

            CurrencyType source = CurrencyType.USD;
            CurrencyType target = CurrencyType.KRW;

            // when
            CurrencyRate currencyRate = exchangeRateProvider.getCurrencyRate(source, target);

            // then
            assertThat(currencyRate.getValue()).isNull();
        }
    }
}
