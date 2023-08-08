package shopping.order.repository;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.cart.domain.vo.Quantity;
import shopping.member.domain.Member;
import shopping.member.repository.MemberRepository;
import shopping.order.domain.Order;
import shopping.order.domain.OrderItem;
import shopping.product.domain.Product;
import shopping.product.repository.ProductRepository;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("findOrderByIdWithFetchJoinOrderItem 테스트")
    void findOrderByIdWithFetchJoinOrderItem() {
        Member member = new Member("test@test.com", "test123");
        memberRepository.save(member);
        Product product = new Product("치킨", "image", "10000");
        productRepository.save(product);
        Order order = new Order(member.getId());
        OrderItem orderItem = new OrderItem(product.getId(), product.getName(), product.getPrice(),
            product.getImage(), new Quantity(3), null);
        order.addOrderItem(orderItem);

        orderRepository.save(order);

        Order findOrder = orderRepository.findOrderByIdWithFetchJoinOrderItem(order.getId()).get();

        Assertions.assertThat(findOrder).isEqualTo(order);
        Assertions.assertThat(findOrder.getOrderItems().getOrderItems().get(0)).isEqualTo(orderItem);
    }

    @Test
    @DisplayName("findAllOrderByMemberId 테스트")
    void findAllOrderByMemberId() {
        Member member = new Member("test@test.com", "test123");
        memberRepository.save(member);

        Order order1 = new Order(member.getId());
        Order order2 = new Order(member.getId());
        Order order3 = new Order(member.getId());

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        List<Order> allOrders = orderRepository.findAllOrderByMemberId(member.getId());

        Assertions.assertThat(allOrders).hasSize(3);
        Assertions.assertThat(allOrders).containsExactly(order1, order2, order3);
    }
}