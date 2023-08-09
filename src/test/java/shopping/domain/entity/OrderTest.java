package shopping.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;

class OrderTest {

    @Test
    @DisplayName("주문을 생성한다")
    void createOrder() {
        // given
        final long userId = 1;
        final List<OrderItem> items = List.of();

        // when & then
        assertThatNoException().isThrownBy(() -> Order.of(userId, items));
    }
}
