package shopping.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import shopping.exception.ShoppingException;

class ProductImageTest {

    @Test
    @DisplayName("상품 이미지를 생성한다.")
    void createProductImage() {
        /* given */
        final String imageUrl = "/assets/img/chicken.png";

        /* when & then */
        assertThatCode(() -> new ProductImage(imageUrl))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("상품 이미지가 null이거나 공백일 때 ShoppingException을 던진다.")
    void createProductImageFailWithNullOrEmptyValue(final String value) {
        /* given */

        
        /* when & then */
        assertThatCode(() -> new ProductImage(value))
            .isInstanceOf(ShoppingException.class)
            .hasMessage("상품 이미지는 비어있거나 공백이면 안됩니다. 입력값: " + value);
    }
}
