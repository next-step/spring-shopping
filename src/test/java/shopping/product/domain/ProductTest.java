package shopping.product.domain;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.common.vo.Image;
import shopping.common.vo.ImageStoreType;
import shopping.exception.WooWaException;

@DisplayName("Product 단위 테스")
class ProductTest {

    @Test
    @DisplayName("price가 양수면 정상적으로 생성된다.")
    void create() {
        Image image = createLocalImage();

        assertThatCode(() -> new Product("name", image, "0"))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("price가 마이너스면 IllegealException을 반환한다")
    void throwExceptionIfPriceIsMinus() {
        Image image = createLocalImage();

        assertThatThrownBy(() -> new Product("name", image, "-1"))
            .isInstanceOf(WooWaException.class)
            .hasCause(new IllegalArgumentException())
            .hasMessage("상품의 가격은 0원보다 작을 수 없습니다.");
    }

    private static Image createLocalImage() {
        return new Image(ImageStoreType.LOCAL_STATIC, "name");
    }
}
