package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import shopping.cart.domain.CartProduct;
import shopping.cart.repository.CartProductRepository;
import shopping.global.exception.ShoppingException;
import shopping.infrastructure.MockExchangeRateApi;
import shopping.infrastructure.dto.ExchangeRateResponse;
import shopping.order.domain.Order;
import shopping.order.dto.OrderResponse;
import shopping.order.repository.OrderRepository;
import shopping.order.service.OrderService;

@DataJpaTest
@Import(MockExchangeRateApi.class)
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, cartProductRepository);
    }

    @Test
    @DisplayName("주문 상품을 정상적으로 생성한다.")
    void 주문_상품_정상적으로_생성_한다() {
        // given
        Long memberId = 1L;
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse(1300D);
        List<CartProduct> cartProducts = cartProductRepository
            .findAllByMemberId(memberId);
        assertThat(cartProducts).isNotEmpty();
        // when
        OrderResponse orderResponse = orderService.saveOrder(memberId, exchangeRateResponse);
        // then
        assertThat(orderResponse.getItems().size()).isEqualTo(cartProducts.size());
    }

    @Test
    @DisplayName("주문 상품이 없을 경우 에러 반환한다.")
    void 주문_상품이_없을_경우_에러_반환_한다() {
        // given
        Long memberId = 3L;
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse(1300D);
        assertThat(cartProductRepository.findAllByMemberId(memberId)).isEmpty();

        // when & then
        assertThatCode(() -> orderService.saveOrder(memberId, exchangeRateResponse))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("주문하실 상품이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("로그인한 유저의 주문상품을 불러온다.")
    void 주문_상품을_정상적으로_불러올_수_있다() {
        // given
        Long memberId = 2L;
        Long orderId = 1L;

        // when & then
        assertThatCode(() -> orderService.getOrder(memberId, orderId))
            .doesNotThrowAnyException();
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
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse(1300D);

        // when & then
        assertThatCode(() -> orderService.saveOrder(memberId, exchangeRateResponse))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("주문하실 상품이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("사용자별 모든 주문을 다 가져온다.")
    void 사용자별_주문_목록_반환() {
        // given 
        Long memberId = 2L;
        List<Order> orderList = orderRepository.findByMemberId(2L);

        // when
        List<OrderResponse> orderResponses = orderService.getOrderList(memberId);

        // then
        assertThat(orderResponses)
            .extracting("items")
            .size()
            .isEqualTo(orderList.size());
    }

}
