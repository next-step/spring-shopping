package shopping.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.entity.Order;
import shopping.domain.fixture.DomainFixture;

import static org.assertj.core.api.Assertions.assertThatNoException;

class OrderResponseTest {

    @Test
    @DisplayName("Order로 OrderResponse를 생성한다.")
    void createOrderResponse() {
        // given
        final Order order = DomainFixture.createOrderWithId();

        // when & then
        assertThatNoException().isThrownBy(() -> OrderResponse.from(order));
    }
}
