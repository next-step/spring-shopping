package shopping.order.app.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.order.app.exception.IllegalExchangeRateException;

@DisplayName("Exchange 클래스")
class ExchangeTest {

    @Nested
    @DisplayName("new 생성자는")
    class new_constructor {

        @Test
        @DisplayName("rate가 0 이하로 주어질경우, IllegalExchangeRateException를 던진다.")
        void throw_illegal_exchange_rate_exception_when_input_zero_rate() {
            // given
            double rate = 0.0D;

            // when
            Exception exception = catchException(() -> new Exchange(rate));

            // then
            assertThat(exception).isInstanceOf(IllegalExchangeRateException.class);
        }
    }

    @Nested
    @DisplayName("exchange 메소드는")
    class exchange_method {

        @Test
        @DisplayName("price를 입력받아, 환율이 계산된 가격을 반환한다.")
        void return_exchanged_price() {
            // given
            BigInteger price = BigInteger.valueOf(10000);
            double rate = 1.345D;
            BigDecimal expected = BigDecimal.valueOf(10000).divide(BigDecimal.valueOf(rate), 5, RoundingMode.HALF_UP);
            Exchange exchange = new Exchange(1.345D);

            // when
            BigDecimal result = exchange.calculate(price);

            // then
            assertThat(expected).isEqualTo(result);
        }
    }
}
