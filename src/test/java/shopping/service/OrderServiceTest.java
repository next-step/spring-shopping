package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static shopping.exception.ShoppingErrorType.INVALID_ORDER_REQUEST;
import static shopping.exception.ShoppingErrorType.NOT_FOUND_ORDER_ID;
import static shopping.fixture.CartItemFixture.createCartItem;
import static shopping.fixture.MemberFixture.MEMBER_ID;
import static shopping.fixture.MemberFixture.createMember;
import static shopping.fixture.OrderFixture.createOrderItems;
import static shopping.fixture.ProductFixture.CHICKEN_IMAGE;
import static shopping.fixture.ProductFixture.CHICKEN_NAME;
import static shopping.fixture.ProductFixture.CHICKEN_PRICE;
import static shopping.fixture.ProductFixture.PIZZA_IMAGE;
import static shopping.fixture.ProductFixture.PIZZA_NAME;
import static shopping.fixture.ProductFixture.PIZZA_PRICE;
import static shopping.fixture.ProductFixture.createChicken;
import static shopping.fixture.ProductFixture.createPizza;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Quantity;
import shopping.domain.member.Member;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;
import shopping.dto.response.OrderHistoryResponse;
import shopping.dto.response.OrderResponse;
import shopping.exception.ShoppingErrorType;
import shopping.exception.ShoppingException;
import shopping.fixture.OrderFixture;
import shopping.infra.ExchangeRateApi;
import shopping.repository.CartItemRepository;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderService.class)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private ExchangeRateApi exchangeRateApi;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    @DisplayName("장바구니에 담긴 상품들 주문 테스트")
    void createOrder() {
        final Member member = createMember(MEMBER_ID);
        final Quantity chickenQuantity = Quantity.from(10);
        final Quantity pizzaQuantity = Quantity.from(5);
        final List<CartItem> cartItems = List.of(
                createCartItem(member, createChicken(), chickenQuantity, 1L),
                createCartItem(member, createPizza(), pizzaQuantity, 2L)
        );
        final List<OrderItem> orderItems = createOrderItems(cartItems, 1L);
        final Long orderId = 1L;
        final Order order = OrderFixture.createOrder(member, orderItems, orderId);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(cartItemRepository.findAllByMemberId(MEMBER_ID)).thenReturn(cartItems);
        when(orderRepository.save(any())).thenReturn(order);

        final OrderResponse orderResponse = orderService.createOrder(MEMBER_ID);

        assertThat(orderResponse.getOrderId()).isEqualTo(orderId);
        assertThat(orderResponse.getOrderItems()).extracting("name", "image", "price", "quantity")
                .contains(
                        Tuple.tuple(CHICKEN_NAME, CHICKEN_IMAGE, CHICKEN_PRICE, chickenQuantity.getValue()),
                        Tuple.tuple(PIZZA_NAME, PIZZA_IMAGE, PIZZA_PRICE, pizzaQuantity.getValue())
                );
    }

    @Test
    @DisplayName("장바구니에 담긴 상품이 없는 경우 주문 시 예외를 던진다.")
    void createOrderWithEmptyCartItems() {
        final Member member = createMember(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(cartItemRepository.findAllByMemberId(MEMBER_ID)).thenReturn(Collections.emptyList());

        final Exception exception = catchException(() -> orderService.createOrder(MEMBER_ID));

        assertThat(exception).isInstanceOf(ShoppingException.class);
        assertThat(exception.getMessage()).isEqualTo(INVALID_ORDER_REQUEST.getMessage());
    }

    @Test
    @DisplayName("주문 상세 조회 테스트")
    void readOrderDetails() {
        final Member member = createMember(MEMBER_ID);
        final Quantity chickenQuantity = Quantity.from(10);
        final Quantity pizzaQuantity = Quantity.from(5);
        final List<OrderItem> orderItems = createOrderItems(List.of(
                createCartItem(member, createChicken(), chickenQuantity, 1L),
                createCartItem(member, createPizza(), pizzaQuantity, 2L)
        ), 1L);
        final Long orderId = 1L;
        final Order order = OrderFixture.createOrder(member, orderItems, orderId);
        when(orderRepository.findOrderWithOrderItemsById(orderId)).thenReturn(Optional.of(order));

        final OrderResponse orderResponse = orderService.readOrderDetail(orderId);

        assertThat(orderResponse.getOrderId()).isEqualTo(orderId);
        assertThat(orderResponse.getOrderItems()).extracting("name", "image", "price", "quantity")
                .contains(
                        Tuple.tuple(CHICKEN_NAME, CHICKEN_IMAGE, CHICKEN_PRICE, chickenQuantity.getValue()),
                        Tuple.tuple(PIZZA_NAME, PIZZA_IMAGE, PIZZA_PRICE, pizzaQuantity.getValue())
                );
    }

    @Test
    @DisplayName("주문 상세 조회 시 해당 주문 번호에 해당하는 주문이 없는 경우 예외를 던진다.")
    void readOrderDetailsWithThrowException() {
        final Long orderId = 1L;
        when(orderRepository.findOrderWithOrderItemsById(orderId)).thenReturn(Optional.empty());

        final Exception exception = catchException(() -> orderService.readOrderDetail(orderId));

        assertThat(exception).isInstanceOf(ShoppingException.class);
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND_ORDER_ID.getMessage());
    }

    @Test
    @DisplayName("주문 목록 조회 시 테스트")
    void readOrderHistories() {
        final Member member = createMember(MEMBER_ID);
        final Quantity chickenQuantity = Quantity.from(10);
        final Quantity pizzaQuantity = Quantity.from(5);
        final List<OrderItem> orderItems = createOrderItems(List.of(
                createCartItem(member, createChicken(), chickenQuantity, 1L),
                createCartItem(member, createPizza(), pizzaQuantity, 2L)
        ), 1L);
        final Long orderId = 1L;
        final Order order = OrderFixture.createOrder(member, orderItems, orderId);
        when(orderRepository.findAllByMemberId(MEMBER_ID)).thenReturn(List.of(order));

        final List<OrderHistoryResponse> orderHistoryResponses = orderService.readOrderHistories(MEMBER_ID);
        assertThat(orderHistoryResponses).hasSize(1);
        assertThat(orderHistoryResponses.get(0).getOrderItems()).extracting("name", "image", "price", "quantity")
                .contains(
                        Tuple.tuple(CHICKEN_NAME, CHICKEN_IMAGE, CHICKEN_PRICE, chickenQuantity.getValue()),
                        Tuple.tuple(PIZZA_NAME, PIZZA_IMAGE, PIZZA_PRICE, pizzaQuantity.getValue())
                );
    }

    @Test
    @DisplayName("장바구니에 담긴 상품들 주문할 경우 API에 환율이 없는 경우 예외를 던진다.")
    void createOrderWithExchangeApiHasNotExchangeRate() {
        final Member member = createMember(MEMBER_ID);
        final Quantity chickenQuantity = Quantity.from(10);
        final Quantity pizzaQuantity = Quantity.from(5);
        final List<CartItem> cartItems = List.of(
                createCartItem(member, createChicken(), chickenQuantity, 1L),
                createCartItem(member, createPizza(), pizzaQuantity, 2L)
        );
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(cartItemRepository.findAllByMemberId(MEMBER_ID)).thenReturn(cartItems);
        when(exchangeRateApi.getUSDtoKRWExchangeRate()).thenReturn(Double.POSITIVE_INFINITY);

        final Exception exception = catchException(() -> orderService.createOrder(MEMBER_ID));

        assertThat(exception).isInstanceOf(ShoppingException.class);
        assertThat(exception.getMessage()).isEqualTo(ShoppingErrorType.ERROR_EXCHANGE_RATE.getMessage());
    }

    @Test
    @DisplayName("외부 API에 오류가 발생한 경우 예외를 던진다.")
    void createOrderWithExchangeApiHasError() {
        when(exchangeRateApi.getUSDtoKRWExchangeRate()).thenThrow(RestClientException.class);

        final Exception exception = catchException(() -> orderService.createOrder(MEMBER_ID));

        assertThat(exception).isInstanceOf(RestClientException.class);
    }
}
