package shopping.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shopping.auth.domain.LoggedInMember;
import shopping.cart.dto.request.CartItemCreationRequest;
import shopping.cart.service.CartService;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.member.repository.MemberRepository;
import shopping.order.OrderMapper;
import shopping.order.domain.Order;
import shopping.order.repository.OrderRepository;
import shopping.product.domain.Product;
import shopping.product.domain.vo.Money;
import shopping.product.repository.ProductRepository;

@DataJpaTest
@Import({OrderService.class, CartService.class, OrderMapper.class})
class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("주문을 생성한다")
    void createOrder() {
        Long memberId = memberRepository.save(new Member("email", "password")).getId();
        Long productId = productRepository.save(new Product("피자", "imageUrl", "10000")).getId();
        Long productId2 = productRepository.save(new Product("치킨", "imageUrl", "20000")).getId();
        LoggedInMember loggedInMember = new LoggedInMember(memberId);
        cartService.addCartItem(loggedInMember, new CartItemCreationRequest(productId));
        cartService.addCartItem(loggedInMember, new CartItemCreationRequest(productId2));
        cartService.addCartItem(loggedInMember, new CartItemCreationRequest(productId2));

        Long orderId = orderService.createOrder(loggedInMember).getId();

        Order order = orderRepository.findById(orderId).orElseThrow();
        assertThat(order.getTotalPrice()).isEqualTo(new Money("50000"));
    }

    @Test
    @DisplayName("주문 번호가 존재하지 않으면 예외를 던진다")
    void notExistOrderThrowException() {
        LoggedInMember loggedInMember = new LoggedInMember(999L);
        assertThatThrownBy(() -> orderService.findOrderById(loggedInMember, 999L))
            .isInstanceOf(WooWaException.class)
            .hasMessage("해당 주문이 존재하지 않습니다. orderId: 999");
    }

    @Test
    @DisplayName("본인의 주문이 아니면 조회할 때 예외를 던진다")
    void notOrderOwnerThrowException() {
        Long memberId = memberRepository.save(new Member("email", "password")).getId();
        Long productId = productRepository.save(new Product("피자", "imageUrl", "10000")).getId();
        LoggedInMember loggedInMember = new LoggedInMember(memberId);
        cartService.addCartItem(loggedInMember, new CartItemCreationRequest(productId));
        Long orderId = orderService.createOrder(loggedInMember).getId();

        LoggedInMember otherLoggedInMember = new LoggedInMember(999L);
        assertThatThrownBy(() -> orderService.findOrderById(otherLoggedInMember, orderId))
            .isInstanceOf(WooWaException.class)
            .hasMessage("해당 주문의 소유자가 아닙니다. orderId: " + orderId);
    }
}
