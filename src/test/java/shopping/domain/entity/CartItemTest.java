package shopping.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.entity.fixture.EntityFixture;

import static org.assertj.core.api.Assertions.assertThat;

class CartItemTest {

    @Test
    @DisplayName("장바구니 항목 수량을 1 증가할 수 있다.")
    void increaseQuantity() {
        // given
        User user = EntityFixture.createUser();
        Product product = EntityFixture.createProduct();
        CartItem cartItem = new CartItem(user, product, Quantity.ONE);

        // when
        cartItem.increaseQuantity();

        // then
        assertThat(cartItem.getQuantity().getValue()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 항목 수량을 업데이트 할 수 있다.")
    void updateQuantity() {
        // given
        User user = EntityFixture.createUser();
        Product product = EntityFixture.createProduct();
        CartItem cartItem = new CartItem(user, product, Quantity.ONE);

        // when
        cartItem.updateQuantity(new Quantity(4));

        // then
        assertThat(cartItem.getQuantity().getValue()).isEqualTo(4);
    }
}
