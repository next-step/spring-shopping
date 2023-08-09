package shopping.cart.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.product.domain.Product;

@DisplayName("Cart 단위 테스트")
class CartTest {

    @Test
    @DisplayName("장바구니에 해당하는 상품의 이름과 가격이 변동되었는지 확인한다")
    void validateProductChange() {

        Product product = new Product(1L, "피자", "imageUrl", "10000");
        Product nameChangedProduct = new Product(1L, "치킨", "imageUrl", "10000");
        Product priceChangedProduct = new Product(1L, "피자", "imageUrl", "9999");
        Member member = new Member(1L, "email", "password");
        Cart cart = new Cart(List.of(new CartItem(product, member)));

        Assertions.assertThatThrownBy(() -> cart.validate(List.of(nameChangedProduct)))
            .isInstanceOf(WooWaException.class)
            .hasMessage("상품이 변경되었습니다.");

        Assertions.assertThatThrownBy(() -> cart.validate(List.of(priceChangedProduct)))
            .isInstanceOf(WooWaException.class)
            .hasMessage("상품이 변경되었습니다.");
    }
}
