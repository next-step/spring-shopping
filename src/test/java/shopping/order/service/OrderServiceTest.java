package shopping.order.service;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shopping.auth.domain.LoggedInMember;
import shopping.cart.domain.CartItem;
import shopping.cart.repository.CartItemRepository;
import shopping.member.domain.Member;
import shopping.member.repository.MemberRepository;
import shopping.order.domain.Order;
import shopping.order.dto.request.OrderCreationRequest;
import shopping.order.repository.OrderRepository;
import shopping.product.domain.Product;
import shopping.product.repository.ProductRepository;

@DataJpaTest
@Import(OrderService.class)
class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("주문 생성 메서드 테스트")
    void addOrder() {
        Member member = makeMember();

        Product chicken = makeProduct("치킨", "10000");
        Product pizza = makeProduct("피자", "15000");
        Product cola = makeProduct("콜라", "1000");

        CartItem chickenCartItem = makeCartItem(member, chicken, 2);
        CartItem pizzaCartItem = makeCartItem(member, pizza, 3);
        CartItem colaCartItem = makeCartItem(member, cola, 10);

        LoggedInMember loggedInMember = new LoggedInMember(member.getId());

        OrderCreationRequest orderCreationRequest = new OrderCreationRequest(
            List.of(chickenCartItem.getId(), pizzaCartItem.getId(), colaCartItem.getId())
        );

        Long orderId = orderService.addOrder(loggedInMember, orderCreationRequest);

        Order makeOrder = orderRepository.findById(orderId).get();
        assertOrder(member, orderId, makeOrder);
    }

    private void assertOrder(Member member, Long orderId, Order order) {
        Assertions.assertThat(order).extracting("id").isEqualTo(orderId);
        Assertions.assertThat(order).extracting("memberId").isEqualTo(member.getId());
        Assertions.assertThat(order).extracting("orderItems")
            .extracting("orderItems").isNotNull();
    }

    private Member makeMember() {
        Member member = new Member("test", "test1");
        memberRepository.save(member);
        return member;
    }

    private Product makeProduct(String name, String price) {
        Product chicken = new Product(name, "image", price);
        productRepository.save(chicken);
        return chicken;
    }

    private CartItem makeCartItem(Member member, Product chicken, int quantity) {
        CartItem chickenCartItem = new CartItem(
            member.getId(),
            chicken.getId(),
            chicken.getName(),
            chicken.getPrice(),
            quantity);
        cartItemRepository.save(chickenCartItem);
        return chickenCartItem;
    }
}
