package shopping.mart.persist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import shopping.mart.domain.Product;

@DisplayName("ProductRepository 클래스")
@ContextConfiguration(classes = {ProductRepository.class})
class ProductRepositoryTest extends JpaTest {

    private static final PageRequest DEFAULT_PAGE_REQUEST = PageRequest.of(0, 10,
            Sort.by(Direction.DESC, "createdAt"));

    @Autowired
    private ProductRepository productRepository;

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
            List<Product> result = productRepository.findAllProducts(DEFAULT_PAGE_REQUEST);

            // then
            assertProducts(result, pizza, beer);
        }

        @Test
        @DisplayName("저장된 product가 없을 경우 빈 리스트를 반환한다.")
        void it_return_empty_list_when_not_exists_products() {
            // when
            List<Product> result = productRepository.findAllProducts(DEFAULT_PAGE_REQUEST);

            // then
            assertThat(result).isEmpty();
        }

        private void assertProducts(final List<Product> result, final Product... expected) {
            assertThat(result).hasSize(expected.length);

            Collections.reverse(result);

            for (int i = 0; i < result.size(); i++) {
                assertProducts(result.get(i), expected[i]);
            }
        }

        private void assertProducts(final Product result, final Product expected) {
            assertThat(result.getId()).isNotNull();
            assertThat(result.getName()).isEqualTo(expected.getName());
            assertThat(result.getImageUrl()).isEqualTo(expected.getImageUrl());
            assertThat(result.getPrice()).isEqualTo(expected.getPrice());
        }
    }
}
