package shopping.order.domain.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.WooWaException;

class ExchangeRateTest {

    @Test
    @DisplayName("양수의 값이 들어오면 환율이 정상적으로 생성된다.")
    void create() {
        assertThatCode(
            () -> new ExchangeRate(new BigDecimal("0.1"))
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("음수의 값이 들어오면 생성에 실패하고 예외가 발생한다.")
    void createException() {
        assertThatThrownBy(
            () -> new ExchangeRate(new BigDecimal("-0.1"))
        ).isInstanceOf(WooWaException.class)
            .hasMessage("환율이 음수일 수 없습니다.");
    }
}
