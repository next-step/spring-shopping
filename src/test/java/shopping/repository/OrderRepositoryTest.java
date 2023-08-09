package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shopping.domain.Member;
import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.domain.Product;

@DataJpaTest
@Import(value = {OrderRepository.class, MemberRepository.class, ProductRepository.class})
class OrderRepositoryTest {

    OrderRepository orderRepository;
    MemberRepository memberRepository;
    ProductRepository productRepository;

    @Autowired
    public OrderRepositoryTest(OrderRepository orderRepository, MemberRepository memberRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Nested
    @DisplayName("findById 메소드는")
    class FindById {

        @Test
        @DisplayName("id에 해당하는 Order 와 OrderItem 리스트를 함께 반환한다.")
        void returnOrderById() {
            // given
            Member member = memberRepository.findById(1L).get();
            Order order = createOrder(member);

            // when
            Optional<Order> result = orderRepository.findById(order.getId());

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(order.getId());
            assertThat(result.get().getOrderItems()).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("findByMemberId 메소드는")
    class FindByMemberId {

        @Test
        @DisplayName("memberId에 해당하는 Order 와 OrderItem 리스트를 함께 반환한다.")
        void returnOrderByMemberId() {
            // given
            Member member = memberRepository.findById(1L).get();
            Order order = createOrder(member);

            // when
            List<Order> result = orderRepository.findByMemberId(member.getId());

            // then
            assertThat(result)
                .hasSize(1)
                .contains(order);
        }
    }

    private Order createOrder(Member member) {
        Order order = new Order(member);
        Product chicken = productRepository.findById(1L).get();
        OrderItem chickenItem = new OrderItem(order, chicken, "chicken", 2000, 1, "image");
        order.addOrderItem(chickenItem);
        orderRepository.save(order);
        return order;
    }
}