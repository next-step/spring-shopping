package shopping.mart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shopping.mart.app.domain.Product;

@DisplayName("ProductPersistService 클래스")
@ContextConfiguration(classes = {ProductPersistService.class})
class ProductPersistServiceTest extends JpaTest {

    @Autowired
    private ProductPersistService productRepository;

    private void assertProduct(final Optional<Product> result, Product product) {
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isNotNull();
        assertThat(result.get().getName()).isEqualTo(product.getName());
        assertThat(result.get().getImageUrl()).isEqualTo(product.getImageUrl());
        assertThat(result.get().getPrice()).isEqualTo(product.getPrice());
    }

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

    @Nested
    @DisplayName("findAllProducts 메소드는")
    class findAllProducts_method {

        @Test
        @DisplayName("저장된 모든 products를 반환한다.")
        void it_return_all_persisted_products() {
            // given
            Product pizza = new Product("피자", "/default-product.png", "10000");
            Product beer = new Product("맥주", "/default-product.png", "20000");

            productRepository.saveProduct(pizza);
            productRepository.saveProduct(beer);

            // when
            List<Product> result = productRepository.findAllProducts();

            // then
            assertProduct(result, pizza, beer);
        }

        @Test
        @DisplayName("저장된 product가 없을 경우 빈 리스트를 반환한다.")
        void it_return_empty_list_when_not_exists_products() {
            // when
            List<Product> result = productRepository.findAllProducts();

            // then
            assertThat(result).isEmpty();
        }

        private void assertProduct(final List<Product> result, final Product... expected) {
            assertThat(result).hasSize(expected.length);
            for (int i = 0; i < result.size(); i++) {
                assertProduct(result.get(i), expected[i]);
            }
        }

        private void assertProduct(final Product result, final Product expected) {
            assertThat(result.getId()).isNotNull();
            assertThat(result.getName()).isEqualTo(expected.getName());
            assertThat(result.getImageUrl()).isEqualTo(expected.getImageUrl());
            assertThat(result.getPrice()).isEqualTo(expected.getPrice());
        }
    }
}
