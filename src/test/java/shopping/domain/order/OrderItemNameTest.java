package shopping.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderItemNameTest {

    @Test
    @DisplayName("주문 아이템 이름을 생성할 수 있다.")
    void createOrderItemName() {
        final String value = "impati";

        final OrderItemName orderItemName = OrderItemName.from(value);

        assertThat(orderItemName.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1234567890123456789012345678901"})
    @DisplayName("빈 문자열이나 null , 30글자 이상의 입력으로 주문 아이템 이름을 생성할 때 예외를 던진다.")
    void validateOrderItemName(final String value) {
        assertThatThrownBy(() -> OrderItemName.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("주문 아이템의 이름이 올바른 형식이 아닙니다.");
    }
}
