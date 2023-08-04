package shopping.entity.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.CartItems;
import shopping.entity.EntityFixture;
import shopping.entity.product.Product;
import shopping.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CartItemsTest {

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCartItem() {
        // given
        User user = EntityFixture.createUser();
        Product product = EntityFixture.createProduct();
        CartItem item = new CartItem(user, product, Quantity.ONE);
        CartItems items = new CartItems(new ArrayList<>());
        // when
        items.add(item);

        // then
        assertThat(items.getItems()).contains(item);
    }

    @Test
    @DisplayName("장바구니에 존재하는 상품을 추가하면 수량이 늘어난다.")
    void addCartItemDuplicate() {
        // given
        User user = EntityFixture.createUser();
        Product product = EntityFixture.createProduct();
        CartItem item = new CartItem(user, product, Quantity.ONE);
        CartItems items = new CartItems(new ArrayList<>());

        // when
        items.add(item);
        items.add(item);

        // then
        assertThat(items.getItems()).hasSize(1);
        assertThat(items.getItems().get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니에 상품이 존재하면 true를 반환한다.")
    void contains() {
        // given
        User user = EntityFixture.createUser();
        Product product = EntityFixture.createProduct();
        CartItem item = new CartItem(1L, user, product, Quantity.ONE);
        CartItems items = new CartItems(new ArrayList<>(List.of(item)));


        // when, then
        assertThat(items.contains(item)).isTrue();
    }

    @Test
    @DisplayName("장바구니에 상품이 존재하지 않으면 false를 반환한다.")
    void notContains() {
        // given
        User user = EntityFixture.createUser();
        Product product = EntityFixture.createProduct();
        CartItem item = new CartItem(1L, user, product, Quantity.ONE);
        CartItems items = new CartItems(new ArrayList<>());


        // when, then
        assertThat(items.contains(item)).isFalse();
    }
}
