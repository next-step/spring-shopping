package shopping.domain.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.auth.PBKDF2PasswordEncoder;
import shopping.auth.PasswordEncoder;
import shopping.domain.user.User;
import shopping.exception.cart.NoCartItemForOrderException;
import shopping.exception.cart.NotSameOrderException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@DisplayName("주문 도메인 테스트")
class OrderItemsTest {

    private final PasswordEncoder encoder = new PBKDF2PasswordEncoder();

    @DisplayName("장바구니 아이템으로 생성")
    @Test
    void createFromCartItems() {
        // given
        List<Product> products = List.of(
                new Product("치킨", "/chicken.jpg", 10_000.0),
                new Product("피자", "/pizza.jpg", 20_000.0),
                new Product("샐러드", "/salad.jpg", 5_000.0)
        );
        User user = new User("test@example.com", "1234", encoder);
        List<CartItem> cartItems = products.stream()
                .map(product -> new CartItem(user.getId(), product))
                .collect(Collectors.toList());
        Order order = new Order(user.getId());

        // when & then
        assertThatNoException().isThrownBy(() -> OrderItems.from(cartItems, order));
    }

    @DisplayName("장바구니에 아이템이 없으면 예외 발생")
    @Test
    void noCartItems() {
        // given
        User user = new User("test@example.com", "1234", encoder);
        List<CartItem> cartItems = List.of();
        Order order = new Order(user.getId());

        // when & then
        assertThatThrownBy(() -> OrderItems.from(cartItems, order))
                .isInstanceOf(NoCartItemForOrderException.class);
    }

    @DisplayName("주문 아이템 목록으로 생성")
    @Test
    void createOfOrderItem() {
        // given
        List<Product> products = List.of(
                new Product("치킨", "/chicken.jpg", 10_000.0),
                new Product("피자", "/pizza.jpg", 20_000.0),
                new Product("샐러드", "/salad.jpg", 5_000.0)
        );
        User user = new User("test@example.com", "1234", encoder);
        List<CartItem> cartItems = products.stream()
                .map(product -> new CartItem(user.getId(), product))
                .collect(Collectors.toList());
        Order order = new Order(user.getId());
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> OrderItem.from(cartItem, order))
                .collect(Collectors.toList());

        // when & then
        assertThatNoException().isThrownBy(() -> OrderItems.of(orderItems));
    }

    @DisplayName("서로 다른 주문을 참조하고 있으면 예외 발생")
    @Test
    void notSameOrder() {
        // given
        List<Product> products = List.of(
                new Product("치킨", "/chicken.jpg", 10_000.0),
                new Product("피자", "/pizza.jpg", 20_000.0),
                new Product("샐러드", "/salad.jpg", 5_000.0)
        );
        List<CartItem> cartItems = products.stream()
                .map(product -> new CartItem(1L, product))
                .collect(Collectors.toList());
        Order order = new Order(1L);
        Order other = new Order(2L);
        List<OrderItem> orderItems = List.of(
                OrderItem.from(cartItems.get(0), order),
                OrderItem.from(cartItems.get(1), order),
                OrderItem.from(cartItems.get(2), other)
        );

        // when & then
        assertThatThrownBy(() -> OrderItems.of(orderItems))
                .isInstanceOf(NotSameOrderException.class);
    }

    @DisplayName("주문 총합 계산")
    @Test
    void totalPrice() {
        // given
        List<Product> products = List.of(
                new Product("치킨", "/chicken.jpg", 10_000.0),
                new Product("피자", "/pizza.jpg", 20_000.0),
                new Product("샐러드", "/salad.jpg", 5_000.0)
        );
        User user = new User("test@example.com", "1234", encoder);
        List<CartItem> cartItems = products.stream()
                .map(product -> new CartItem(user.getId(), product))
                .collect(Collectors.toList());

        ExchangeRate exchangeRate = new ExchangeRate(1200.0, CurrencyType.USD, CurrencyType.KRW);
        Order order = new Order(user.getId(), exchangeRate);
        OrderItems orderItems = OrderItems.from(cartItems, order);

        // when
        Money totalMoney = orderItems.totalPrice();

        // then
        assertThat(totalMoney).isEqualTo(new Money(35000.0));
    }

    @DisplayName("환율 기반 총합 계산")
    @Test
    void exchangePrice() {
        // given
        List<Product> products = List.of(
                new Product("치킨", "/chicken.jpg", 10_000.0),
                new Product("피자", "/pizza.jpg", 20_000.0),
                new Product("샐러드", "/salad.jpg", 5_000.0)
        );
        User user = new User("test@example.com", "1234", encoder);
        List<CartItem> cartItems = products.stream()
                .map(product -> new CartItem(user.getId(), product))
                .collect(Collectors.toList());

        ExchangeRate exchangeRate = new ExchangeRate(1200.0, CurrencyType.USD, CurrencyType.KRW);
        Order order = new Order(user.getId(), exchangeRate);
        OrderItems orderItems = OrderItems.from(cartItems, order);

        // when
        Optional<Money> exchangePrice = orderItems.exchangePrice();

        // then
        assertThat(exchangePrice).isPresent().get().isEqualTo(new Money(35000 / exchangeRate.getRate(), CurrencyType.USD));
    }
}
