package shopping.cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.product.domain.Product;

@DisplayName("CartItem 단위 테스트")
class CartItemTest {

    @Test
    @DisplayName("상품의 변경이 확인되면 true를 반환한다.")
    void isProductChanged() {
        Member member = new Member(1L, "member", "홍길동");
        Product noneChangedProduct = new Product("특가 피자", "imageUrl", "5000");
        Product nameChangedProduct = new Product("피자", "imageUrl", "5000");
        Product priceChangedProduct = new Product("특가 피자", "imageUrl", "10000");
        CartItem cartItem = new CartItem(noneChangedProduct, member);

        Assertions.assertAll(
            assertThat(cartItem.isProductChanged(noneChangedProduct))::isFalse,
            assertThat(cartItem.isProductChanged(nameChangedProduct))::isTrue,
            assertThat(cartItem.isProductChanged(priceChangedProduct))::isTrue
        );
    }

    @Test
    @DisplayName("본인의 장바구니가 아니면 예외를 던진다.")
    void validateMyCartItem() {
        Product product = new Product("특가 피자", "imageUrl", "5000");
        CartItem cartItem = new CartItem(product, new Member(1L, "member", "홍길동"));
        Long memberId = 1L;
        Long otherMemberId = 10000L;

        assertThatCode(() -> cartItem.validateMyCartItem(memberId)).doesNotThrowAnyException();
        assertThatThrownBy(() -> cartItem.validateMyCartItem(otherMemberId))
            .isInstanceOf(WooWaException.class)
            .hasMessage("본인의 장바구니가 아닙니다. memberId: '" + otherMemberId + "' cartItemId: '" + cartItem.getId() + "'");
    }
}
