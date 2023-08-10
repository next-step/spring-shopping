package shopping.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.domain.entity.Order;
import shopping.domain.entity.OrderItem;
import shopping.domain.entity.fixture.EntityFixture;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("주문 번호와 유저 번호로 주문을 찾는다.")
    void findByIdAndUserId() {
        // given
        final long userId = 1L;
        final Order order = EntityFixture.createOrder(userId);
        final Long id = orderRepository.save(order).getId();

        // when
        final Optional<Order> persistOrder = orderRepository.findByIdAndUserId(id, userId);

        // then
        assertThat(persistOrder).isPresent();
    }

    @Test
    @DisplayName("주문 번호에 해당하는 주문이 없을 때 Optional Empty를 반환한다.")
    void findNullByIdAndUserId() {
        // given
        final long userId = 1L;
        final Order order = EntityFixture.createOrder(userId);
        final Long id = orderRepository.save(order).getId();

        // when
        final Optional<Order> persistOrder = orderRepository.findByIdAndUserId(id + 1, userId);

        // then
        assertThat(persistOrder).isEmpty();
    }

    @Test
    @DisplayName("유저 번호로 주문을 찾는다.")
    void findAllByUserId() {
        // given
        final long userId = 1L;
        final List<OrderItem> orderItems = List.of(EntityFixture.createOrderItem());
        final Order order = EntityFixture.createOrder(userId);
        orderRepository.save(order);

        // when
        final List<Order> persistOrder = orderRepository.findAllByUserId(userId);

        // then
        assertThat(persistOrder).hasSize(orderItems.size());
    }
}
