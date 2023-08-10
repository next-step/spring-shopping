package shopping.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.domain.entity.Order;
import shopping.domain.entity.fixture.EntityFixture;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("주문 번호와 유저 번호로 주문을 찾는다")
    void findByIdAndUserId() {
        // given
        final Long userId = 1L;
        final Order order = Order.of(userId, List.of(EntityFixture.createOrderItem()));
        final Long id = orderRepository.save(order).getId();

        // when
        final Optional<Order> persistOrder = orderRepository.findByIdAndUserId(id, userId);

        // then
        assertThat(persistOrder.isPresent()).isTrue();
    }
}
