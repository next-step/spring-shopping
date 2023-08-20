package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import shopping.exception.ShoppingException;

@DisplayName("주문 상품 이름 테스트")
class OrderProductNameTest {

    @Test
    @DisplayName("주문 상품 이름을 생성한다.")
    void createOrderProductName() {
        /* given */
        final String value = "마라탕";

        /* when & then */
        assertThatCode(() -> new OrderProductName(value))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("주문 상품 이름이 null이거나 공백일 때 ShoppingException을 던진다.")
    void createOrderProductNameFailWithNullOrEmptyValue(final String value) {
        /* given */


        /* when & then */
        assertThatCode(() -> new OrderProductName(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("이름은 비어있거나 공백이면 안됩니다.");
    }

    @Test
    @DisplayName("주문 상품 이름이 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final OrderProductName origin = new OrderProductName("마라탕");
        final OrderProductName another = new OrderProductName("마라탕");

        /* when & then */
        assertThat(origin)
            .isEqualTo(another)
            .hasSameHashCodeAs(another);
    }
}
