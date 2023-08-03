package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shopping.domain.CartProduct;
import shopping.domain.Product;

@DisplayName("ProductRepository 클래스")
@DataJpaTest
@Import(value = {ProductRepository.class})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("findAll 메소드는")
    class FindAll {

        @Test
        @DisplayName("모든 Product 를 반환한다")
        void returnAllProducts() {
            // when
            List<Product> result = productRepository.findAll();

            // then
            assertThat(result).hasSize(8);
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class FindById {

        @Test
        @DisplayName("id 에 해당하는 Product 를 반환한다")
        void returnProductById() {
            // given
            Long productId = 1L;

            // when
            Optional<Product> result = productRepository.findById(productId);

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(productId);
        }
    }
}