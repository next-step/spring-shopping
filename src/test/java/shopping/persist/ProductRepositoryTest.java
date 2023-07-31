package shopping.persist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shopping.domain.Product;

@ContextConfiguration(classes = {ProductRepository.class})
class ProductRepositoryTest extends JpaTest {

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("findByProductName 메소드는")
    class findByProductName_method {

        private final Product product = new Product("제육덮밥", "/default-product.png", "10000");

        @Test
        @DisplayName("이름에 해당하는 Product를 반환한다.")
        void it_return_name_matched_product() {
            // given
            productRepository.saveProduct(product);

            // when
            Optional<Product> result = productRepository.findByProductName(product.getName());

            // then
            assertProduct(result);
        }

        @Test
        @DisplayName("이름에 해당하는 product가 없으면, Optional.empty를 반환한다.")
        void it_return_empty_optional_when_not_exists_product() {
            // when
            Optional<Product> result = productRepository.findByProductName(product.getName());

            // then
            assertThat(result).isNotPresent();
        }

        private void assertProduct(final Optional<Product> result) {
            assertThat(result).isPresent();
            assertThat(result.get().getId()).isNotNull();
            assertThat(result.get().getName()).isEqualTo(product.getName());
            assertThat(result.get().getImageUrl()).isEqualTo(product.getImageUrl());
            assertThat(result.get().getPrice()).isEqualTo(product.getPrice());
        }
    }

}
