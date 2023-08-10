package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shopping.auth.PBKDF2PasswordEncoder;
import shopping.auth.PasswordEncoder;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Order;
import shopping.domain.cart.Product;
import shopping.domain.user.User;
import shopping.dto.web.response.OrderResponse;
import shopping.exception.auth.UserNotMatchException;
import shopping.exception.cart.NoCartItemForOrderException;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("주문 서비스 통합 테스트")
class OrderServiceTest extends ServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final PasswordEncoder encoder = new PBKDF2PasswordEncoder();

    @DisplayName("정상 주문 생성 요청")
    @Test
    void createOrder() {
        // given
        String email = "test@example.com";
        String password = "1234";
        User savedUser = saveUser(email, password);
        Long userId = savedUser.getId();

        String name = "치킨";
        String imageUrl = "/chicken.jpg";
        Long price = 10_000L;
        Product product = new Product(name, imageUrl, price);
        productRepository.save(product);

        CartItem cartItem = new CartItem(savedUser.getId(), product);
        cartItemRepository.save(cartItem);

        // when
        OrderResponse orderResponse = orderService.createOrder(userId);
        Optional<Order> order = orderRepository.findByUserId(userId);

        // then
        assertThat(order).isPresent();
        assertThat(orderResponse.getId()).isEqualTo(order.get().getId());
        assertThat(orderResponse.getTotalPrice()).isEqualTo(price);
        assertThat(cartItemRepository.findAllByUserId(userId)).isEmpty();
    }

    @DisplayName("장바구니에 물건이 없으면 예외 발생")
    @Test
    void noCartItemForOrder() {
        // given
        String email = "test@example.com";
        String password = "1234";
        User savedUser = saveUser(email, password);
        Long userId = savedUser.getId();

        // when, then
        assertThatThrownBy(() -> orderService.createOrder(userId))
                .isInstanceOf(NoCartItemForOrderException.class);
    }

    @DisplayName("정상 주문 세부 내역 조회 요청")
    @Test
    void getOrder() {
        // given
        String email = "test@example.com";
        String password = "1234";
        User savedUser = saveUser(email, password);
        Long userId = savedUser.getId();

        String name = "치킨";
        String imageUrl = "/chicken.jpg";
        Long price = 10_000L;
        Product product = new Product(name, imageUrl, price);
        productRepository.save(product);

        CartItem cartItem = new CartItem(savedUser.getId(), product);
        cartItemRepository.save(cartItem);
        OrderResponse orderResponse = orderService.createOrder(userId);

        // when
        OrderResponse response = orderService.findOrderById(userId, orderResponse.getId());

        // then
        assertThat(response)
                .extracting(OrderResponse::getId, OrderResponse::getTotalPrice)
                .containsExactly(orderResponse.getId(), orderResponse.getTotalPrice());
        assertThat(response.getItems()).usingRecursiveComparison().isEqualTo(orderResponse.getItems());
    }

    @DisplayName("다른 유저 주문 내역을 조회할 때 예외 발생")
    @Test
    void getOtherUserOrder() {
        // given
        String email = "test@example.com";
        String password = "1234";
        User savedUser = saveUser(email, password);
        Long userId = savedUser.getId();

        User otherUser = saveUser("notsame@example.com", "1234");
        Long otherId = otherUser.getId();

        String name = "치킨";
        String imageUrl = "/chicken.jpg";
        Long price = 10_000L;
        Product product = new Product(name, imageUrl, price);
        productRepository.save(product);

        CartItem cartItem = new CartItem(savedUser.getId(), product);
        cartItemRepository.save(cartItem);
        OrderResponse orderResponse = orderService.createOrder(userId);
        Long orderId = orderResponse.getId();

        // when, then
        assertThatThrownBy(() -> orderService.findOrderById(otherId, orderId))
                .isInstanceOf(UserNotMatchException.class);
    }

    private User saveUser(String email, String digest) {
        User user = new User(email, digest, encoder);
        return userRepository.save(user);
    }
}
