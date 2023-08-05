package shopping.cart.domain.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.WooWaException;

class QuantityTest {

    @Test
    @DisplayName("수량이 양수이면 정상적으로 생성된다.")
    void create() {
        assertThatCode(() -> new Quantity(1)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수량이 0 미만이면 예외를 던진다.")
    void validateNoneNegative() {
        assertThatThrownBy(() -> new Quantity(-1))
            .isInstanceOf(WooWaException.class)
            .hasMessage("수량은 음수일 수 없습니다 quantity: '" + -1 + "'");
    }
}
