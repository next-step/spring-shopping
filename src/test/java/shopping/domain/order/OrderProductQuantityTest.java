package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

@DisplayName("주문 상품 개수 테스트")
class OrderProductQuantityTest {

    @Test
    @DisplayName("주문 상품 개수를 정상적으로 생성한다.")
    void create() {
        /* given */
        final int value = 5;

        /* when & then */
        assertThatCode(() -> new OrderProductQuantity(value))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("주문 상품 개수가 1개 미만일 경우 ShoppingException을 던진다.")
    void createFailWithLessThanOne(final int value) {
        /* given */


        /* when & then */
        assertThatThrownBy(() -> new OrderProductQuantity(value))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("주문 상품 개수는 1개 이상이어야 합니다.");
    }
}
