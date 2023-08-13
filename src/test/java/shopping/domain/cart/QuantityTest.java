package shopping.domain.cart;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.general.InvalidRequestException;

@DisplayName("수량 도메인 테스트")
class QuantityTest {

    @DisplayName("수량은 양수여야 한다.")
    @Test
    void positiveQuantity() {
        assertThatNoException().isThrownBy(() -> new Quantity(1));
    }

    @DisplayName("수량이 1이하면 예외를 던진다.")
    @Test
    void notPositiveQuantity() {
        assertThatThrownBy(() -> new Quantity(0))
                .isInstanceOf(InvalidRequestException.class);
    }
}
