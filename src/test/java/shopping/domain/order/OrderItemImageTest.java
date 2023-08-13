package shopping.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import shopping.exception.ShoppingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderItemImageTest {

    @Test
    @DisplayName("주문 아이템의 이미지 주소를 생성한다.")
    void createOrderItemImage() {
        final String value = "/image/Chicken.png";

        final OrderItemImage orderItemImage = OrderItemImage.from(value);

        assertThat(orderItemImage.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("빈 문자열이나 null 이 들어오면 주문 아이템 이미지 주소를 생성할 때 예외를 던진다.")
    void validateOrderItemImage(final String value) {
        assertThatThrownBy(() -> OrderItemImage.from(value))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("주문 아이템의 이미지 주소가 올바른 형식이 아닙니다.");
    }
}
