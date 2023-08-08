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
import shopping.repository.CartItemRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;
import shopping.repository.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;

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
        cartItemRepository.save(new CartItem(anyMember, anyProduct));

        final OrderCreateResponse orderCreateResponse = orderService.createOrder(anyMember.getId());

        assertThat(cartItemRepository.findAllByMemberId(anyMember.getId())).isEmpty();
        assertThat(orderRepository.findById(orderCreateResponse.getOrderId())).isPresent();
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
