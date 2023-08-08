package shopping.cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.WooWaException;
import shopping.product.domain.Product;
import shopping.product.domain.vo.Money;

@DisplayName("CartItem 단위 테스트")
class CartItemTest {

    @Test
    @DisplayName("상품의 변경이 확인되면 true를 반환한다.")
    void isProductChanged() {
        CartItem cartItem = new CartItem(1L, 1L, "특가 피자", new Money("5000"), 3);
        Product noneChangedProduct = new Product("특가 피자", "imageUrl", "5000");
        Product nameChangedProduct = new Product("피자", "imageUrl", "5000");
        Product priceChangedProduct = new Product("특가 피자", "imageUrl", "10000");

        Assertions.assertAll(
            assertThat(cartItem.isProductChanged(noneChangedProduct))::isFalse,
            assertThat(cartItem.isProductChanged(nameChangedProduct))::isTrue,
            assertThat(cartItem.isProductChanged(priceChangedProduct))::isTrue
        );
    }

    @Test
    @DisplayName("본인의 장바구니가 아니면 예외를 던진다.")
    void validateMyCartItem() {
        CartItem cartItem = new CartItem(1L, 1L, "특가 피자", new Money("5000"), 3);
        Long memberId = 1L;
        Long otherMemberId = 10000L;

        assertThatCode(() -> cartItem.validateMyCartItem(memberId)).doesNotThrowAnyException();
        assertThatThrownBy(() -> cartItem.validateMyCartItem(otherMemberId))
            .isInstanceOf(WooWaException.class)
            .hasMessage("본인의 장바구니가 아닙니다. memberId: '" + otherMemberId + "' cartItemId: '" + cartItem.getId() + "'");
    }
}
