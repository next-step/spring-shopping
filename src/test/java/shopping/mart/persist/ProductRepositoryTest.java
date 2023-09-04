package shopping.mart.persist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import shopping.mart.domain.Product;
import shopping.mart.persist.repository.ProductJpaRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductRepository 클래스")
@ContextConfiguration(classes = {ProductRepository.class})
class ProductRepositoryTest extends JpaTest {

    private static final PageRequest DEFAULT_PAGE_REQUEST = PageRequest.of(0, 10,
            Sort.by(Direction.DESC, "id"));

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Nested
    @DisplayName("findAllProducts 메소드는")
    class findAllProducts_method {

        @Test
        @DisplayName("저장된 모든 products를 반환한다.")
        void it_return_all_persisted_products() {
            // given
            Product soju = new Product(1L, "소주", "images/soju.jpeg", "5000");
            Product beer = new Product(2L, "맥주", "images/beer.jpeg", "5500");
            Product makgeolli = new Product(3L,"막걸리", "images/makgeolli.png", "6000");

            // when
            List<Product> result = productRepository.findAllProducts(DEFAULT_PAGE_REQUEST);
            for (Product product : result) {
                System.out.println(product.getId() + " : " + product.getName());
            }
            // then
            assertProducts(result, soju, beer, makgeolli);
        }

        @Test
        @DisplayName("저장된 product가 없을 경우 빈 리스트를 반환한다.")
        void it_return_empty_list_when_not_exists_products() {
            // given
            productJpaRepository.deleteAll();

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
