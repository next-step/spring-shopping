package shopping.application;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import shopping.api.ExchangeRateAPICaller;
import shopping.domain.CartItem;
import shopping.domain.Email;
import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.dto.request.ExchangeRate;
import shopping.exception.CurrencyApiFailException;
import shopping.exception.EmptyCartException;
import shopping.exception.UserNotFoundException;
import shopping.repository.CartItemRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

@Transactional
@SpringBootTest
@DisplayName("주문 생성자 통합 테스트")
class OrderCreatorTest {

    @Autowired
    private OrderCreator orderCreator;

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

    @MockBean
    private ExchangeRateAPICaller exchangeRateAPICaller;

    @Nested
    @DisplayName("주문 생성 테스트")
    class WhenCreateOrder {

        @DisplayName("이메일에 해당하는 유저가 없을 때 예외 발생")
        @Test
        void givenNoUserThenThrow() {
            // given
            String email = "test1@testing.com";
            Mockito.when(exchangeRateAPICaller.getExchangeRate())
                    .thenReturn(new ExchangeRate(1300.0));

            // when, then
            assertThatCode(() -> orderCreator.createOrder(email))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("외부 api 통신 실패 때 예외 발생")
        @Test
        void givenApiFailThenThrow() {
            // given
            String email = "test1@testing.com";
            Mockito.when(exchangeRateAPICaller.getExchangeRate())
                    .thenThrow(new CurrencyApiFailException());

            // when, then
            assertThatCode(() -> orderCreator.createOrder(email))
                    .isInstanceOf(CurrencyApiFailException.class);
        }

        @DisplayName("장바구니 비었을 때 예외 발생")
        @Test
        void givenEmptyCartThenThrow() {
            // given
            String email = "test1@testing.com";
            userRepository.save(new User(email, "userpasswordisthis"));
            Mockito.when(exchangeRateAPICaller.getExchangeRate())
                    .thenReturn(new ExchangeRate(1300.0));

            // when, then
            assertThatCode(() -> orderCreator.createOrder(email))
                    .isInstanceOf(EmptyCartException.class);
        }

        @DisplayName("장바구니에 물건이 한개 일때 정상 처리")
        @Test
        void givenOneCartItemThenReturn() {
            // given
            String email = "test1234@example.com";
            Mockito.when(exchangeRateAPICaller.getExchangeRate())
                    .thenReturn(new ExchangeRate(1300.0));
            User savedUser = userRepository.save(new User(email, "1234"));
            Product savedProduct1 = productRepository.save(
                    new Product(9871L, "chicken1", "/chicken.jpg", 10_000L));
            CartItem cartItem1 = cartItemRepository.save(new CartItem(savedUser, savedProduct1));

            // when
            Long orderId = orderCreator.createOrder(email);

            // then
            Order order = orderRepository.findById(orderId).orElseThrow();
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(new Email(email));
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(order.getUser()).isEqualTo(savedUser);
                softAssertions.assertThat(order.getTotalPrice().getPrice()).isEqualTo(
                        savedProduct1.getPrice());
                softAssertions.assertThat(orderItems).hasSize(1);
                softAssertions.assertThat(orderItems.get(0)).extracting(
                        OrderItem::getName,
                        OrderItem::getImageUrl,
                        OrderItem::getPrice,
                        OrderItem::getQuantity
                ).contains(
                        savedProduct1.getName(),
                        savedProduct1.getImageUrl(),
                        savedProduct1.getPrice(),
                        1
                );
                softAssertions.assertThat(cartItems).isEmpty();
            });
        }
    }
}
