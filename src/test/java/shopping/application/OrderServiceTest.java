package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartItem;
import shopping.domain.Email;
import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.exception.EmptyCartException;
import shopping.exception.UserNotFoundException;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

@Transactional
@SpringBootTest
@DisplayName("주문 서비스 통합 테스트")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Nested
    @DisplayName("주문 생성 서비스 테스트")
    class WhenCreateOrder {

        @DisplayName("이메일에 해당하는 유저가 없을 때 예외 발생")
        @Test
        void givenNoUserThenThrow() {
            // given
            String email = "test1@testing.com";

            // when, then
            assertThatCode(() -> orderService.createOrder(email))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("장바구니 비었을 때 예외 발생")
        @Test
        void givenEmptyCartThenThrow() {
            // given
            String email = "test1@testing.com";
            userRepository.save(new User(email, "userpasswordisthis"));

            // when, then
            assertThatCode(() -> orderService.createOrder(email))
                    .isInstanceOf(EmptyCartException.class);
        }

        @DisplayName("장바구니에 물건이 한개 일때 정상 처리")
        @Test
        void givenOneCartItemThenReturn() {
            // given
            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(email, "1234"));
            Product savedProduct1 = productRepository.save(
                    new Product(9871L, "chicken1", "/chicken.jpg", 10_000L));
            CartItem cartItem1 = cartItemRepository.save(new CartItem(savedUser, savedProduct1));

            // when
            Long orderId = orderService.createOrder(email);

            // then
            Order order = orderRepository.findById(orderId).orElseThrow();
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(new Email(email));
            assertAll(
                    () -> assertThat(order.getUser()).isEqualTo(savedUser),
                    () -> assertThat(order.getTotalPrice().getPrice()).isEqualTo(
                            savedProduct1.getPrice()),
                    () -> assertThat(orderItems).hasSize(1),
                    () -> assertThat(orderItems.get(0)).extracting(
                            OrderItem::getName,
                            OrderItem::getImageUrl,
                            OrderItem::getPrice,
                            OrderItem::getQuantity
                    ).contains(
                            savedProduct1.getName(),
                            savedProduct1.getImageUrl(),
                            savedProduct1.getPrice(),
                            1
                    ),
                    () -> assertThat(cartItems).isEmpty()
            );
        }

        @DisplayName("장바구니에 물건이 여러개 일때 정상 처리")
        @Test
        void givenMultiCartItemThenReturn() {
            // given
            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(email, "1234"));
            Product savedProduct1 = productRepository.save(
                    new Product(9871L, "chicken1", "/chicken.jpg", 10_000L));
            CartItem cartItem1 = cartItemRepository.save(
                    new CartItem(30L, savedUser, savedProduct1, 3));
            Product savedProduct2 = productRepository.save(
                    new Product(9872L, "chicken2", "/chicken.jpg", 30_000L));
            CartItem cartItem2 = cartItemRepository.save(new CartItem(savedUser, savedProduct2));

            // when
            Long orderId = orderService.createOrder(email);

            // then
            Order order = orderRepository.findById(orderId).orElseThrow();
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(new Email(email));
            assertAll(
                    () -> assertThat(order.getUser()).isEqualTo(savedUser),
                    () -> assertThat(order.getTotalPrice().getPrice()).isEqualTo(
                            60_000L),
                    () -> assertThat(orderItems).hasSize(2),
                    () -> assertThat(orderItems.get(0)).extracting(
                            OrderItem::getName,
                            OrderItem::getImageUrl,
                            OrderItem::getPrice,
                            OrderItem::getQuantity
                    ).contains(
                            savedProduct1.getName(),
                            savedProduct1.getImageUrl(),
                            savedProduct1.getPrice(),
                            3
                    ),
                    () -> assertThat(orderItems.get(1)).extracting(
                            OrderItem::getName,
                            OrderItem::getImageUrl,
                            OrderItem::getPrice,
                            OrderItem::getQuantity
                    ).contains(
                            savedProduct2.getName(),
                            savedProduct2.getImageUrl(),
                            savedProduct2.getPrice(),
                            1
                    ),
                    () -> assertThat(cartItems).isEmpty()
            );
        }
    }

}
