package shopping.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.exception.ShoppingException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;

class ImageTest {

    @Test
    @DisplayName("이미지 주소는 255자 이하이다.")
    void createImangeWithLength255() {
        // when, then
        assertThatNoException().isThrownBy(() -> new Image("a".repeat(255)));
    }

    @Test
    @DisplayName("이미지 주소는 255자를 넘으면 오류를 반환한다.")
    void createImangeWithLength256() {
        // when, then
        assertThatCode(() -> new Image("a".repeat(256)))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("이미지 주소는 255자를 넘을 수 없습니다.");
    }

    @DataJpaTest
    static
    class ProductRepositoryTest {

        @Autowired
        ProductRepository productRepository;

        @Test
        @DisplayName("모든 상품을 조회한다.")
        void findAll() {
            // when
            List<Product> products = productRepository.findAll();

            // then
            assertThat(products).hasSize(2);
        }
    }
}