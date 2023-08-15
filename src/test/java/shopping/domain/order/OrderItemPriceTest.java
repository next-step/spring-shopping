package shopping.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThatCode;

class OrderItemPriceTest {

    @Test
    @DisplayName("가격이 음수이면 오류를 반환한다.")
    void createPriceWithNegative() {
        // when, then
        assertThatCode(() -> new OrderItemPrice(-1))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("가격은 음수일 수 없습니다.");
    }
}