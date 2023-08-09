package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.global.exception.ShoppingException;
import shopping.order.domain.vo.Money;

@DisplayName("가격 테스트")
class MoneyTest {

    @Test
    @DisplayName("가격을 생성한다.")
    void createMoney() {
        /* given */
        final int value = 1000;

        /* when & then */
        assertThatCode(() -> new Money(value))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("가격이 0이하 인 경우, ShoppingException을 던진다.")
    void createMoneyFailWithLessThanZero() {
        // when & then
        int value = -1;
        assertThatCode(() -> new Money(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("주문 가격은 0이하면 안됩니다. 입력값: " + value);
    }

}
