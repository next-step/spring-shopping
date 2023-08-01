package shopping.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartItemTest {

    @Test
    @DisplayName("장바구니 항목 수량을 1 증가할 수 있다.")
    void increaseQuantity() {
        // given
        Product product = new Product();
        User user = new User();
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
        Product product = new Product();
        User user = new User();
        CartItem cartItem = new CartItem(user, product, 1);

        // when
        cartItem.updateQuantity(4);

        // then
        assertThat(cartItem.getQuantity()).isEqualTo(4);
    }
}