package shopping.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemPriceTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1000, 20000, 1_000_000_000})
    @DisplayName("주문 아이템 가격을 생성한다.")
    void createPrice(final int value) {
        final OrderItemPrice orderItemPrice = OrderItemPrice.from(value);

        assertThat(orderItemPrice.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -21000000, 1_000_000_001})
    @DisplayName("주문 아이템 가격이 0 미만 10억초과이면 예외를 던진다.")
    void validatePrice(final int value) {
        Assertions.assertThatThrownBy(() -> OrderItemPrice.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("가격은 0원보다 크고 10억보다 같거나 낮아야합니다.");
    }
}
