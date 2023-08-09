package shopping.order.service;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shopping.auth.domain.LoggedInMember;
import shopping.cart.domain.CartItem;
import shopping.cart.domain.vo.Quantity;
import shopping.cart.repository.CartItemRepository;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.member.repository.MemberRepository;
import shopping.order.domain.Order;
import shopping.order.domain.OrderItem;
import shopping.order.dto.request.OrderCreationRequest;
import shopping.order.dto.response.OrderDetailResponse;
import shopping.order.dto.response.OrdersResponse;
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

    @Test
    @DisplayName("주문을 생성할 때 장바구니가 비어 있다면 예외를 던진다.")
    void addOrderExceptionWhenCartIsEmpty() {
        Member member = makeMember();
        LoggedInMember loggedInMember = new LoggedInMember(member.getId());
        OrderCreationRequest orderCreationRequest = new OrderCreationRequest(Collections.emptyList());

        Assertions.assertThatThrownBy(
                () -> orderService.addOrder(loggedInMember, orderCreationRequest)
            ).isInstanceOf(WooWaException.class)
            .hasMessage("장바구니에 상품이 없습니다.");
    }

    @Test
    @DisplayName("주문 상세 조회 메서드 테스트")
    void findOrderDetail() {
        Member member = makeMember();

        Product chicken = makeProduct("치킨", "10000");
        Product pizza = makeProduct("피자", "15000");
        Product cola = makeProduct("콜라", "1000");

        LoggedInMember loggedInMember = new LoggedInMember(member.getId());

        Order order = new Order(member.getId());

        makeOrderItem(chicken, 3, order);
        makeOrderItem(pizza, 2, order);
        makeOrderItem(cola, 3, order);

        orderRepository.save(order);

        OrderDetailResponse findOrder = orderService.getOrderDetail(loggedInMember, order.getId());

        Assertions.assertThat(findOrder).extracting("orderId").isEqualTo(order.getId());
        Assertions.assertThat(findOrder).extracting("totalPrice").isEqualTo("63000");
    }

    @Test
    @DisplayName("주문 전체 조회 메서드 테스트")
    void findOrders() {
        Member member = makeMember();

        Product chicken = makeProduct("치킨", "10000");
        Product pizza = makeProduct("피자", "15000");
        Product cola = makeProduct("콜라", "1000");

        LoggedInMember loggedInMember = new LoggedInMember(member.getId());

        Order order1 = new Order(member.getId());
        Order order2 = new Order(member.getId());

        makeOrderItem(chicken, 3, order1);
        makeOrderItem(pizza, 2, order1);
        makeOrderItem(cola, 3, order2);

        orderRepository.save(order1);
        orderRepository.save(order2);

        OrdersResponse orders = orderService.getOrders(loggedInMember);

        Assertions.assertThat(orders.getOrders()).hasSize(2);
        Assertions.assertThat(orders.getOrders().get(0).getOrderItems()).hasSize(2);
        Assertions.assertThat(orders.getOrders().get(1).getOrderItems()).hasSize(1);
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

    private void makeOrderItem(Product product, int quantity, Order order) {
        OrderItem chickenOrderItem = new OrderItem(product.getId(), product.getName(), product.getPrice(), product.getImage(), new Quantity(
            quantity), null);
        order.addOrderItem(chickenOrderItem);
    }
}
