package shopping.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatNoException;

class OrderTest {

    @Test
    @DisplayName("주문을 생성한다")
    void createOrder() {
        // given
        final long userId = 1;
        final Set<OrderItem> items = Set.of();
        final Price totalPrice = new Price(1000);

        // when & then
        assertThatNoException().isThrownBy(() -> new Order(userId, items, totalPrice));
    }
}
