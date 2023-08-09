package shopping.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
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
import shopping.repository.CartItemRepository;
import shopping.repository.OrderRepository;
import shopping.repository.UserRepository;

@DisplayName("OrderService")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private UserRepository userRepository;
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

        // (1) orderEntity 저장
        Long totalPrice = 70000L;
        OrderEntity order = new OrderEntity(totalPrice, user);

        OrderItemEntity orderItemChicken = OrderItemEntity.from(cartItemChicken, order);
        OrderItemEntity orderItemPizza = OrderItemEntity.from(cartItemPizza, order);
        List<OrderItemEntity> orderItems = List.of(orderItemChicken, orderItemPizza);
        order.addOrderItems(orderItems);
        lenient().when(orderRepository.save(any(OrderEntity.class))).thenReturn(order);

        // when
        orderService.orderCartItem(userId);

        // then
        verify(userRepository).getReferenceById(userId);
        verify(cartItemRepository).findByUserId(userId);
        verify(orderRepository).save(any(OrderEntity.class));
    }

}
