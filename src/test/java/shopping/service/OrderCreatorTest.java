package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import shopping.cart.dto.request.CartProductCreateRequest;
import shopping.cart.dto.response.CartResponse;
import shopping.cart.service.CartService;
import shopping.infrastructure.ExchangeRateApi;
import shopping.infrastructure.MockExchangeRateApi;
import shopping.order.dto.OrderResponse;
import shopping.order.service.OrderCreator;
import shopping.order.service.OrderService;

@DataJpaTest
@ActiveProfiles("test")
@Import({OrderService.class, MockExchangeRateApi.class, CartService.class})
public class OrderCreatorTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ExchangeRateApi exchangeRateApi;
    @Autowired
    private CartService cartService;

    private OrderCreator orderCreator;

    @BeforeEach
    public void setUp() {
        orderCreator = new OrderCreator(orderService, exchangeRateApi);
    }

    @Test
    @DisplayName("외부 API 를 통해 환율을 전달하고 주문 상품을 생산하는 로직을 실행한다.")
    void 외부_API_환율_계산과_주문_상품_생산() {
        // when
        Long memberId = 3L;
        Long productId = 1L;
        cartService.createCartProduct(
            memberId, new CartProductCreateRequest(productId));
        List<CartResponse> allCartProducts = cartService.findAllCartProducts(memberId);

        // when
        OrderResponse order = orderCreator.createOrder(memberId);

        // then
        assertThat(order.getItems().size()).isEqualTo(allCartProducts.size());
    }
}
