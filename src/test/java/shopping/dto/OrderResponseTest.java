package shopping.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.entity.Order;
import shopping.domain.fixture.DomainFixture;

import static org.assertj.core.api.Assertions.assertThat;

class OrderResponseTest {

    @Test
    @DisplayName("Order로 OrderResponse를 생성한다.")
    void createOrderResponse() {
        // given
        final Order order = DomainFixture.createOrderWithId();

        // when
        final OrderResponse response = OrderResponse.from(order);

        // then
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getUserId()).isEqualTo(1);
        assertThat(response.getTotalPrice()).isEqualTo(1);
        assertThat(response.getTotalConvertedPrice()).isNotInfinite();
        assertThat(response.getItems()).hasSize(1);
    }
}
