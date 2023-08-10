package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.entity.CartItemEntity;
import shopping.domain.entity.OrderEntity;
import shopping.domain.entity.OrderItemEntity;
import shopping.domain.entity.ProductEntity;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.CurrencyRequest;
import shopping.dto.response.OrderItemResponse;
import shopping.dto.response.OrderResponse;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderRepository;
import shopping.repository.UserRepository;
import shopping.utils.CurrencyLayer;

@DisplayName("OrderService")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CurrencyLayer currencyLayer;
    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("성공 : 장바구니 상품 주문 성공")
    void successOrderCartItem() {
        // given
        Long userId = 1L;
        UserEntity user = new UserEntity(userId, "test_email@woowafriends.com", "test_password!");
        ProductEntity chicken = new ProductEntity(1L, "치킨", "fried_chicken.png", 20000);
        ProductEntity pizza = new ProductEntity(2L, "피자", "pizza.png", 25000);
        CartItemEntity cartItemChicken = new CartItemEntity(1L, user, chicken, 1);
        CartItemEntity cartItemPizza = new CartItemEntity(2L, user, pizza, 2);

        // (0) cartItem List get
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        List<CartItemEntity> cartItems = List.of(cartItemChicken, cartItemPizza);
        when(cartItemRepository.findByUserId(userId)).thenReturn(cartItems);
        HashMap<String, Double> hm = new HashMap<>();
        hm.put("USDKRW", 1300D);
        CurrencyRequest currencyRequest = new CurrencyRequest(true, "USD", hm);

        // (1) orderEntity 저장
        int totalPrice = 70000;
        OrderEntity order = new OrderEntity(totalPrice, 0D, user);

        OrderItemEntity orderItemChicken = OrderItemEntity.from(cartItemChicken, order, 0D);
        OrderItemEntity orderItemPizza = OrderItemEntity.from(cartItemPizza, order, 0D);
        List<OrderItemEntity> orderItems = List.of(orderItemChicken, orderItemPizza);
        order.addOrderItems(orderItems);
        lenient().when(orderRepository.save(any(OrderEntity.class))).thenReturn(order);

        // when
        orderService.orderCartItem(currencyRequest, userId);

        // then
        verify(userRepository).getReferenceById(userId);
        verify(cartItemRepository).findByUserId(userId);
        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    @DisplayName("성공 : 특정 주문의 상세 정보를 확인")
    void findOrderByOrderId() {
        // given
        Long userId = 1L;
        UserEntity user = new UserEntity(userId, "test_email@woowafriends.com", "test_password!");
        ProductEntity chicken = new ProductEntity(1L, "치킨", "fried_chicken.png", 20000);
        ProductEntity pizza = new ProductEntity(2L, "피자", "pizza.png", 25000);
        CartItemEntity cartItemChicken = new CartItemEntity(1L, user, chicken, 1);
        CartItemEntity cartItemPizza = new CartItemEntity(2L, user, pizza, 2);

        int totalPrice = 70000;
        OrderEntity order = new OrderEntity(1L, totalPrice, 0D, user, new ArrayList<OrderItemEntity>());
        OrderItemEntity orderItemChicken = new OrderItemEntity(
            1L,
            cartItemChicken.getProduct().getName(),
            cartItemChicken.getProduct().getImageFileName(),
            cartItemChicken.getProduct().getPrice() * cartItemChicken.getQuantity(),
            0D,
            cartItemChicken.getQuantity(),
            order
        );
        OrderItemEntity orderItemPizza = new OrderItemEntity(
            2L,
            cartItemPizza.getProduct().getName(),
            cartItemPizza.getProduct().getImageFileName(),
            cartItemPizza.getProduct().getPrice() * cartItemPizza.getQuantity(),
            0D,
            cartItemPizza.getQuantity(),
            order
        );
        List<OrderItemEntity> orderItems = List.of(orderItemChicken, orderItemPizza);
        order.addOrderItems(orderItems);

        OrderItemResponse chickenResponse = new OrderItemResponse(
            1L,
            "치킨",
            "fried_chicken.png",
            20000,
            0D,
            1);
        OrderItemResponse pizzaResponse = new OrderItemResponse(
            2L,
            "피자",
            "pizza.png",
            50000,
            0D,
            2);
        OrderResponse expectedOrderResponse = new OrderResponse(
            1L,
            70000,
            0D,
            List.of(chickenResponse, pizzaResponse)
        );

        // when
        Long orderId = 1L;
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderResponse orderResponse = orderService.findOrder(orderId, userId);

        // then
        verify(userRepository).getReferenceById(userId);
        verify(orderRepository).findById(orderId);
        assertThat(orderResponse.getTotalPrice()).isEqualTo(expectedOrderResponse.getTotalPrice());
        assertThat(orderResponse.getOrderItems().size()).isEqualTo(expectedOrderResponse.getOrderItems().size());
    }

    @Test
    @DisplayName("성공 : 사용자 별 주문 목록 확인")
    void findOrderByUser() {
        // given
        Long userId = 1L;
        UserEntity user = new UserEntity(userId, "test_email@woowafriends.com", "test_password!");
        ProductEntity chicken = new ProductEntity(1L, "치킨", "fried_chicken.png", 20000);
        ProductEntity pizza = new ProductEntity(2L, "피자", "pizza.png", 25000);
        CartItemEntity cartItemChicken = new CartItemEntity(1L, user, chicken, 1);
        CartItemEntity cartItemPizza = new CartItemEntity(2L, user, pizza, 2);

        int totalPrice = 70000;
        OrderEntity order = new OrderEntity(1L, totalPrice, 0D, user, new ArrayList<OrderItemEntity>());
        OrderItemEntity orderItemChicken = new OrderItemEntity(
            1L,
            cartItemChicken.getProduct().getName(),
            cartItemChicken.getProduct().getImageFileName(),
            cartItemChicken.getProduct().getPrice() * cartItemChicken.getQuantity(),
            0D,
            cartItemChicken.getQuantity(),
            order
        );
        OrderItemEntity orderItemPizza = new OrderItemEntity(
            2L,
            cartItemPizza.getProduct().getName(),
            cartItemPizza.getProduct().getImageFileName(),
            cartItemPizza.getProduct().getPrice() * cartItemPizza.getQuantity(),
            0D,
            cartItemPizza.getQuantity(),
            order
        );
        List<OrderItemEntity> orderItems = List.of(orderItemChicken, orderItemPizza);
        order.addOrderItems(orderItems);

        // when
        Long orderId = 1L;
        when(orderRepository.findAllByUserId(userId)).thenReturn(List.of(order));
        List<OrderResponse> orderResponses = orderService.findOrders(userId);

        // then
        verify(orderRepository).findAllByUserId(userId);
        assertThat(orderResponses.size()).isEqualTo(1);
    }

}
