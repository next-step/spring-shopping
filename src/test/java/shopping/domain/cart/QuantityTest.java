package shopping.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

class QuantityTest {

    @ParameterizedTest
    @DisplayName("장바구니의 상품 수량의 개수를 생성하는데 성공한다.")
    @ValueSource(ints = {1, 100, 1000})
    void createQuantity(final int value) {
        Quantity quantity = Quantity.from(value);

        assertThat(quantity.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @DisplayName("장바구니의 상품 수량의 개수는 한개 보다 적거나 1000보다 많은 경우 생성하는데 실패한다.")
    @ValueSource(ints = {-1, 0, 1001})
    void validateQuantity(final int value) {
        Assertions.assertThatThrownBy(() -> Quantity.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("장바구니 상품 수량 개수는 1개 이상 1000개 이하여야합니다.");
    }
}
