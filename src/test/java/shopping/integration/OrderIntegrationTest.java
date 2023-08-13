package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.exception.ShoppingException;
import shopping.repository.CartProductRepository;
import shopping.service.OrderService;

@SpringBootTest
class OrderIntegrationTest {

    @Autowired
    OrderService orderService;

    @Autowired
    CartProductRepository cartProductRepository;

    @Test
    @DisplayName("주문 생성 성공 시 장바구니를 모두 삭제한다.")
    void deleteAllCartProductWhenSuccessfullyCreateOrder() {
        /* given */
        final Long memberId = 1L;

        /* when */
        orderService.createOrder(memberId);

        /* then */
        assertThat(cartProductRepository.findAllByMemberId(memberId)).isEmpty();
    }

    @Test
    @DisplayName("장바구니에 상품이 없을 경우 주문을 생성 시 ShoppingException을 던진다.")
    void createOrderFailWithEmptyCart() {
        /* given */
        final Long memberId = 777L;

        /* when & then */
        assertThatThrownBy(() -> orderService.createOrder(memberId))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("장바구니가 비어있습니다.");
    }

}
