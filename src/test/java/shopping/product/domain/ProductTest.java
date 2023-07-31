package shopping.product.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exception.WooWaException;

@DisplayName("Product 단위 테스")
class ProductTest {

    @Test
    @DisplayName("price가 마이너스면 IllegealException을 반환한다")
    void throwExceptionIfPriceIsMinus() {
        Assertions.assertThatThrownBy(() -> new Product("name", "path", "-1"))
            .isInstanceOf(WooWaException.class)
            .hasCause(new IllegalArgumentException())
            .hasMessage("상품의 가격은 0원보다 작을 수 없습니다.");

        Assertions.assertThatCode(() -> new Product("name", "path", "0"))
            .doesNotThrowAnyException();
    }
}
