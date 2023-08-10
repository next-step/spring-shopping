package shopping.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThat;

class OrderPriceTest {

    @ParameterizedTest
    @ValueSource(longs = {0L, 1000L, 1_000_000_000_000L})
    @DisplayName("주문 총 가격을 생성한다.")
    void createOrderPrice(final long value) {
        final OrderPrice orderPrice = OrderPrice.from(value);

        assertThat(orderPrice.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 1_000_000_000_001L})
    @DisplayName("주문 총 가격이 0 미만 1조 초과이면 예외를 던진다.")
    void validateOrderPrice(final long value) {
        Assertions.assertThatThrownBy(() -> OrderPrice.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("주문 총 가격은 0원보다 크고 1조보다 같거나 낮아야합니다.");
    }
}
