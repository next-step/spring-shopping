package shopping.cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.product.domain.Product;

@DisplayName("Cart 단위 테스트")
class CartTest {

    @Test
    @DisplayName("장바구니에 해당하는 상품의 이름과 가격이 변동되었다면 예외를 발생시킨다")
    void validateProductChange() {

        Product product = new Product(1L, "피자", "imageUrl", "10000");
        Product nameChangedProduct = new Product(1L, "치킨", "imageUrl", "10000");
        Product priceChangedProduct = new Product(1L, "피자", "imageUrl", "9999");
        Member member = new Member(1L, "email", "password");
        Cart cart = new Cart(List.of(new CartItem(product, member)));

        assertThatThrownBy(() -> cart.validate(List.of(nameChangedProduct)))
            .isInstanceOf(WooWaException.class)
            .hasMessage("상품이 변경되었습니다.");

        assertThatThrownBy(() -> cart.validate(List.of(priceChangedProduct)))
            .isInstanceOf(WooWaException.class)
            .hasMessage("상품이 변경되었습니다.");
    }

    @Test
    @DisplayName("장바구니에 아이템이 없는 경우 예외를 발생시킨다")
    void validateEmptyCart() {
        Cart cart = new Cart(List.of());

        assertThatThrownBy(() -> cart.validate(List.of()))
            .isInstanceOf(WooWaException.class)
            .hasMessage("장바구니에 아이템이 없습니다.");
    }
}
