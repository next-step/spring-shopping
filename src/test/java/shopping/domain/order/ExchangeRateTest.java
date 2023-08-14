package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.global.exception.ShoppingException;
import shopping.order.domain.vo.ExchangeRate;

class ExchangeRateTest {

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0})
    @DisplayName("환율은 0 미만이면 안된다.")
    void 환율_0_이하일_경우_에러_반환(Double value) {
        assertThatCode(() -> new ExchangeRate(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("환율은 0미만이면 안됩니다. 입력값: " + value);
    }

    @Test
    @DisplayName("환율은 0 보다 클 경우, 정상 동작")
    void 환율_0보다_클_경우_정상_동작() {
        assertThatCode(() -> new ExchangeRate(1.0D))
            .doesNotThrowAnyException();

    }
}
