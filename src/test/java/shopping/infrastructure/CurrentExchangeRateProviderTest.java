package shopping.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.application.ExchangeRateProvider;
import shopping.domain.ExchangeRate;

@SpringBootTest
@DisplayName("CurrentExchangeRateProvider 클래스")
class CurrentExchangeRateProviderTest {

    @Autowired
    ExchangeRateProvider exchangeRateProvider;

    @Nested
    @DisplayName("getExchange 메소드는")
    class getExchange_Method {

        @DisplayName("입력 quote 에 따른 환율 정보를 가져온다")
        @Test
        void getExchange() {
            // given
            String quote = "USDKRW";

            // when
            ExchangeRate exchange = exchangeRateProvider.getExchange(quote);

            // then
            assertThat(exchange.getValue()).isPositive();
        }
    }
}