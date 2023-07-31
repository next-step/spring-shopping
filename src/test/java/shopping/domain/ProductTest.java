package shopping.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("상품을 생성한다.")
    void createProduct() {
        /* given */
        final String name = "치킨";
        final String imageUrl = "image.png";
        final int price = 20000;

        /* when & then */
        assertThatCode(() -> new Product(name, imageUrl, price))
            .doesNotThrowAnyException();
    }
}
