package shopping.cart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import shopping.auth.domain.entity.User;
import shopping.auth.repository.UserRepository;
import shopping.cart.domain.entity.CartItem;
import shopping.cart.domain.entity.Order;
import shopping.cart.domain.entity.OrderItem;
import shopping.cart.domain.entity.Product;
import shopping.cart.domain.vo.ExchangeRate;
import shopping.cart.domain.vo.Quantity;
import shopping.cart.domain.vo.WonMoney;
import shopping.cart.dto.response.OrderDetailResponse;
import shopping.cart.dto.response.OrderHistoryResponse;
import shopping.cart.dto.response.OrderItemResponse;
import shopping.cart.dto.response.OrderResponse;
import shopping.cart.repository.CartItemRepository;
import shopping.cart.repository.OrderRepository;
import shopping.cart.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static shopping.TestUtils.createProductWithoutId;
import static shopping.TestUtils.createUserWithoutId;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = OrderService.class))
@DisplayName("OrderService 통합 테스트")
@ActiveProfiles("no-init-test")
class OrderServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("주문에 성공한다.")
    void orderSuccess() {
        /* given */
        final User user = userRepository.save(createUserWithoutId());
        final Product chicken = productRepository.save(createProductWithoutId("chicken", 10000));
        cartItemRepository.save(new CartItem(user, chicken));

        /* when */
        final OrderResponse orderResponse = orderService.order(user.getId(), new ExchangeRate(1300));

        /* then */
        final Order order = orderRepository.findById(orderResponse.getOrderId()).get();
        assertThat(order.getUser()).isEqualTo(user);
        assertThat(order.getOrderItems()).hasSize(1);
        assertThat(order.getTotalPrice()).isEqualTo(new WonMoney(10000));
        assertThat(order.getExchangeRate()).isEqualTo(new ExchangeRate(1300));
        final OrderItem firstOrderItem = order.getOrderItems().get(0);
        assertThat(firstOrderItem.getProductName()).isEqualTo("chicken");
        assertThat(firstOrderItem.getPrice()).isEqualTo(new WonMoney(10000));
        assertThat(firstOrderItem.getQuantity()).isEqualTo(new Quantity(1));
    }

    @Test
    @DisplayName("주문을 상세 조회한다.")
    void getOrderDetail() {
        /* given */
        final User user = userRepository.save(createUserWithoutId());
        final Product chicken = productRepository.save(createProductWithoutId("chicken", 13000));
        final CartItem cartItem = cartItemRepository.save(new CartItem(user, chicken));
        final Order order = orderRepository.save(Order.from(user, List.of(cartItem), new ExchangeRate(1300)));

        /* when */
        final OrderDetailResponse orderDetail = orderService.getOrderDetail(order.getId(), user.getId());

        /* then */
        assertThat(orderDetail.getOrderId()).isEqualTo(order.getId());
        assertThat(orderDetail.getOrderItems()).hasSize(1);
        assertThat(orderDetail.getExchangeRate()).isEqualTo(1300);
        assertThat(orderDetail.getTotalPrice()).isEqualTo(13000);
        assertThat(orderDetail.getDollarTotalPrice()).isEqualTo(10);
        final OrderItemResponse orderItemResponse = orderDetail.getOrderItems().get(0);
        assertThat(orderItemResponse.getProductName()).isEqualTo("chicken");
        assertThat(orderItemResponse.getPrice()).isEqualTo(13000);
        assertThat(orderItemResponse.getQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("주문목록을 조회한다.")
    void getOrderHistory() {
        /* given */
        final User user = userRepository.save(createUserWithoutId());
        final Product chicken = productRepository.save(createProductWithoutId("chicken", 13000));
        final CartItem cartItem = cartItemRepository.save(new CartItem(user, chicken));
        final Order order = orderRepository.save(Order.from(user, List.of(cartItem), new ExchangeRate(1300)));

        /* when */
        final List<OrderHistoryResponse> orderHistory = orderService.getOrderHistory(user.getId());

        /* then */
        assertThat(orderHistory).hasSize(1);
        final OrderHistoryResponse orderHistoryResponse = orderHistory.get(0);
        assertThat(orderHistoryResponse.getOrderId()).isEqualTo(order.getId());
        assertThat(orderHistoryResponse.getOrderItems()).hasSize(1);
        final OrderItemResponse orderItemResponse = orderHistoryResponse.getOrderItems().get(0);
        assertThat(orderItemResponse.getProductName()).isEqualTo("chicken");
        assertThat(orderItemResponse.getPrice()).isEqualTo(13000);
        assertThat(orderItemResponse.getQuantity()).isEqualTo(1);
    }
}
