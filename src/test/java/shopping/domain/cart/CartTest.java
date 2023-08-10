package shopping.domain.cart;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ShoppingException;

@DisplayName("장바구니 테스트")
class CartTest {

    @Test
    @DisplayName("장바구니 물품이 없는 장바구니를 생성할 시 ShoppingException을 던진다.")
    void createFailWithEmptyCartProduct() {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new Cart(1L, Collections.emptyList()))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("장바구니가 비어있습니다.");
    }
}
