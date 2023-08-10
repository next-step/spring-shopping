package shopping.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemQuantityTest {

    @Test
    @DisplayName("주문 아이템 수량의 개수를 생성하는데 성공한다.")
    void createQuantity() {
        final int value = 1000;

        OrderItemQuantity orderItemQuantity = OrderItemQuantity.from(value);

        assertThat(orderItemQuantity.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @DisplayName("주문 아이템의 수량의 개수는 한개 보다 적거나 1000보다 많은 경우 생성하는데 실패한다.")
    @ValueSource(ints = {-1, 0, 1001})
    void validateQuantity(final int value) {
        Assertions.assertThatThrownBy(() -> OrderItemQuantity.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("주문 아이템 수량 개수는 1개 이상 1000개 이하여야합니다.");
    }
}
