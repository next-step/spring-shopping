package shopping.domain.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.auth.PBKDF2PasswordEncoder;
import shopping.auth.PasswordEncoder;
import shopping.domain.user.User;
import shopping.exception.general.InvalidRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("장바구니 상품 도메인 테스트")
class CartItemTest {

    private final PasswordEncoder encoder = new PBKDF2PasswordEncoder();

    @DisplayName("수량을 1개 더한다.")
    @Test
    void addQuantity() {
        User user = new User("admin@example.com", "1234", encoder);
        Product product = new Product("치킨", "/chicken.jpg", 10_000.0);
        CartItem cartItem = new CartItem(user.getId(), product);
        Quantity quantity = cartItem.getQuantity();
        assertThat(cartItem.addQuantity().getQuantity().getQuantity()).isEqualTo(quantity.getQuantity() + 1);
    }

    @DisplayName("수량을 임의의 숫자로 바꾼다.")
    @Test
    void updateQuantity() {
        User user = new User("admin@example.com", "1234", encoder);
        Product product = new Product("치킨", "/chicken.jpg", 10_000.0);
        CartItem cartItem = new CartItem(user.getId(), product);
        assertThat(cartItem.updateQuantity(5).getQuantity().getQuantity()).isEqualTo(5);
    }

    @DisplayName("수량을 0 이하로 바꿀 수 없다.")
    @Test
    void notPositiveQuantity() {
        User user = new User("admin@example.com", "1234", encoder);
        Product product = new Product("치킨", "/chicken.jpg", 10_000.0);
        CartItem cartItem = new CartItem(user.getId(), product);
        assertThatThrownBy(() -> cartItem.updateQuantity(0)).isInstanceOf(InvalidRequestException.class);
    }
}
