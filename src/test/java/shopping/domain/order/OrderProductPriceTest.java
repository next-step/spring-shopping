package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

@DisplayName("주문 상품 가격 테스트")
class OrderProductPriceTest {

    @Test
    @DisplayName("주문 상품 가격을 생성한다.")
    void createOrderProductPrice() {
        /* given */
        final int value = 1000;

        /* when & then */
        assertThatCode(() -> new OrderProductPrice(value))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("주문 상품 가격이 0이하 인 경우, ShoppingException을 던진다.")
    void createOrderProductPriceFailWithLessThanEqualZero(final int value) {
        assertThatCode(() -> new OrderProductPrice(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("가격은 0 이하일 수 없습니다.");
    }

    @Test
    @DisplayName("주문 상품 가격이 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final OrderProductPrice origin = new OrderProductPrice(100);
        final OrderProductPrice another = new OrderProductPrice(100);

        /* when & then */
        assertThat(origin)
            .isEqualTo(another)
            .hasSameHashCodeAs(another);
    }
}
