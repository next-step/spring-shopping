package shopping.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

class ProductTest {
    @Test
    @DisplayName("상품은 id, 이름, 이미지, 가격 정보를 가지고 있다.")
    void createProduct() {
        assertThatNoException().isThrownBy(() -> new Product(1L, "승윤", "img", 10000));
    }
}