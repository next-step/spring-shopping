package shopping.domain.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.DomainFixture;
import shopping.domain.product.Product;

import static org.assertj.core.api.Assertions.assertThat;

class CartItemTest {

    @Test
    @DisplayName("장바구니 항목 수량을 1 증가할 수 있다.")
    void increaseQuantity() {
        // given
        Product product = DomainFixture.createProduct();
        CartItem cartItem = new CartItem(1L, product);

        // when
        cartItem.increaseQuantity();

        // then
        assertThat(cartItem.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 항목 수량을 업데이트 할 수 있다.")
    void updateQuantity() {
        // given
        Product product = DomainFixture.createProduct();
        CartItem cartItem = new CartItem(1L, product);

        // when
        cartItem.updateQuantity(4);

        // then
        assertThat(cartItem.getQuantity()).isEqualTo(4);
    }

    @Test
    @DisplayName("장바구니 항목 총 금액을 계산한다.")
    void calculateTotalPrice() {
        // given
        Product product = DomainFixture.createProduct();
        CartItem cartItem = new CartItem(1L, product, new Quantity(1_000_000));

        // when
        assertThat(cartItem.calculateTotalPrice()).isEqualTo(20_000_000_000L);

    }
}