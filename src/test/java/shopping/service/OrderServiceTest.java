package shopping.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import shopping.cart.repository.CartProductRepository;
import shopping.exchange.MockExchangeRateApi;
import shopping.global.exception.ShoppingException;
import shopping.order.domain.Order;
import shopping.order.repository.OrderRepository;
import shopping.order.service.OrderService;
import shopping.util.ExchangeRateApi;

@DataJpaTest
@Import(MockExchangeRateApi.class)
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private ExchangeRateApi exchangeRateApi;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, cartProductRepository, exchangeRateApi);
    }

    @Test
    @DisplayName("주문 상품이 없는 경우, 에러를 반환한다.")
    void 주문_상품_없을_경우_에러_반환() {
        // given
        Long memberId = 1L;
        Long orderId = 23L;

        // when & then
        assertThatCode(() -> orderService.getOrder(memberId, orderId))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("찾으시는 주문이 존재하지 않습니다. 입력 값 : " + orderId);
    }

    @Test
    @DisplayName("주문한 주문자와 로그인한 유저가 다를 경우, 에러를 반환한다.")
    void 주문_유저_다를_때_에러_반환() {
        // given
        List<Order> order = orderRepository.findByMemberId(2L);
        Long orderId = order.get(0).getId();

        // when & then
        assertThatCode(() -> orderService.getOrder(1L, orderId))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("주문자가 현재 로그인한 사람이 아닙니다. 입력 값 : 1");
    }

    @Test
    @DisplayName("주문을 생성할 때, 주문 상품이 없다면 에러를 반환한다.")
    void 주문_생성_시_주문_상품_없다면_에러_반환() {
        // given
        Long memberId = 3L;

        // when & then
        assertThatCode(() -> orderService.saveOrder(memberId))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("주문하실 상품이 존재하지 않습니다.");
    }

}
