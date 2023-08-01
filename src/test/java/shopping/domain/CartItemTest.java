package shopping.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartItemTest {

    @Test
    @DisplayName("장바구니 항목 수량을 증가할 수 있다.")
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
}