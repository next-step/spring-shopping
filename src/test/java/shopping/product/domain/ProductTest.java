package shopping.product.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.WooWaException;
import shopping.product.domain.vo.Image;

@DisplayName("Product 단위 테스트")
class ProductTest {

    @Test
    @DisplayName("price가 마이너스면 IllegealException을 반환한다")
    void throwExceptionIfPriceIsMinus() {
        Image image = Image.from("name");

        Assertions.assertThatThrownBy(() -> new Product("name", image, "-1"))
            .isInstanceOf(WooWaException.class)
            .hasCause(new IllegalArgumentException())
            .hasMessage("상품의 가격은 0원보다 작을 수 없습니다.");

        Assertions.assertThatCode(() -> new Product("name", image, "0"))
            .doesNotThrowAnyException();
    }
}
