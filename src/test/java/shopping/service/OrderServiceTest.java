package shopping.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.product.Product;
import shopping.dto.response.OrderCreateResponse;
import shopping.dto.response.OrderResponse;
import shopping.dto.response.OrderResponses;
import shopping.repository.CartItemRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;
import shopping.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
@Import(OrderService.class)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("사용자 장바구니에 있는 상품을 주문한다.")
    void createOrder() {
        Member anyMember = getAnyMember();
        Product anyProduct = getAnyProduct();
        addCartItem(anyMember, anyProduct);

        final OrderCreateResponse orderCreateResponse = createOrder(anyMember);

        assertThat(cartItemRepository.findAllByMemberId(anyMember.getId())).isEmpty();
        assertThat(orderRepository.findById(orderCreateResponse.getOrderId())).isPresent();
    }

    @Test
    @DisplayName("주문 아이디로 주문 내역을 조회한다.")
    void readOrder() {
        Member anyMember = getAnyMember();
        Product anyProduct = getAnyProduct();
        addCartItem(anyMember, anyProduct);
        final OrderCreateResponse order = createOrder(anyMember);

        final OrderResponse orderResponse = orderService.readOrder(order.getOrderId());

        assertThat(orderResponse.getOrderId()).isEqualTo(order.getOrderId());
        assertThat(orderResponse.getOrderPrice()).isEqualTo(anyProduct.getPrice());
        assertThat(orderResponse.getOrderItems()).hasSize(1)
                .extracting("image", "name", "price", "quantity")
                .contains(tuple(anyProduct.getImage(), anyProduct.getName(), anyProduct.getPrice(), 1));
    }

    @Test
    @DisplayName("사용자의 모든 주문 내역을 조회한다.")
    void readOrders() {
        Member anyMember = getAnyMember();
        Product anyProduct = getAnyProduct();
        addCartItem(anyMember, anyProduct);
        final OrderCreateResponse firstOrder = createOrder(anyMember);
        addCartItem(anyMember, anyProduct);
        final OrderCreateResponse secondOrder = createOrder(anyMember);

        final OrderResponses orderResponses = orderService.readOrders(anyMember.getId());

        assertThat(orderResponses.getOrders()).hasSize(2)
                .extracting("orderId", "orderPrice")
                .contains(
                        tuple(firstOrder.getOrderId(), anyProduct.getPrice()),
                        tuple(secondOrder.getOrderId(), anyProduct.getPrice())
                );
    }

    private OrderCreateResponse createOrder(final Member anyMember) {
        return orderService.createOrder(anyMember.getId());
    }

    private void addCartItem(final Member anyMember, final Product anyProduct) {
        cartItemRepository.save(new CartItem(anyMember, anyProduct));
    }

    private Member getAnyMember() {
        return memberRepository.findAll().stream()
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    private Product getAnyProduct() {
        return productRepository.findAll().stream()
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }
}