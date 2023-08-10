package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.domain.order.Order;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("Order를 하나 조회하는 경우 OrderProduct 정보를 함께 가져온다.")
    void findByIdWithOrderProduct() {
        /* given */
        final Long memberId = 1L;
        final Long orderId = 1L;

        /* when */
        final Order order = orderRepository.findByIdAndMemberIdWithOrderProduct(orderId, memberId)
            .orElseThrow(() -> new IllegalStateException("테스트 케이스가 존재하지 않습니다."));

        /* then */
        assertThat(order.getOrderProducts()).hasSize(3);
    }
}
