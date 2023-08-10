package shopping.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.entity.Order;
import shopping.domain.entity.fixture.EntityFixture;

import static org.assertj.core.api.Assertions.assertThatNoException;

class OrderResponseTest {

    @Test
    @DisplayName("Order로 OrderResponse를 생성한다.")
    void createOrderResponse() {
        // given
        final Order order = EntityFixture.createOrderWithId();

        // when & then
        assertThatNoException().isThrownBy(() -> OrderResponse.from(order));
    }
}
