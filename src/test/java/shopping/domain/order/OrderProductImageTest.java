package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import shopping.exception.ShoppingException;

@DisplayName("주문 상품 이미지 테스트")
class OrderProductImageTest {

    @Test
    @DisplayName("주문 상품 이미지를 생성한다.")
    void createOrderProductImage() {
        /* given */
        final String imageUrl = "/assets/img/chicken.png";

        /* when & then */
        assertThatCode(() -> new OrderProductImage(imageUrl))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("주문 상품 이미지가 null이거나 공백일 때 ShoppingException을 던진다.")
    void createOrderProductImageFailWithNullOrEmptyValue(final String value) {
        /* given */


        /* when & then */
        assertThatCode(() -> new OrderProductImage(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("이미지 경로는 비어있거나 공백이면 안됩니다.");
    }

    @Test
    @DisplayName("주문 상품 이미지가 동일하면 동일한 객체이다.")
    void equals() {
        /* given */
        final OrderProductImage origin = new OrderProductImage("/assets/img/chicken.png");
        final OrderProductImage another = new OrderProductImage("/assets/img/chicken.png");

        /* when & then */
        assertThat(origin)
            .isEqualTo(another)
            .hasSameHashCodeAs(another);
    }
}
