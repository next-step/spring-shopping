package shopping.domain.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.product.Product;
import shopping.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;

class CartItemTest {

    @Test
    @DisplayName("장바구니 항목 수량을 1 증가할 수 있다.")
    void increaseQuantity() {
        // given
        User user = new User(1L, "test@test.com", "test");
        Product product = new Product(1L, "치킨", "chicken.png", 2000);
        CartItem cartItem = new CartItem(user, product, 1);

        // when
        cartItem.increaseQuantity();

        // then
        assertThat(cartItem.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 항목 수량을 업데이트 할 수 있다.")
    void updateQuantity() {
        // given
        User user = new User(1L, "test@test.com", "test");
        Product product = new Product(1L, "치킨", "chicken.png", 2000);
        CartItem cartItem = new CartItem(user, product, 1);

        // when
        cartItem.updateQuantity(4);

        // then
        assertThat(cartItem.getQuantity()).isEqualTo(4);
    }
}