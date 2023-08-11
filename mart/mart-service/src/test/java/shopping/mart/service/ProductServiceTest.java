package shopping.mart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.mart.domain.usecase.product.response.ProductResponse;
import shopping.mart.domain.Product;
import shopping.mart.domain.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
@DisplayName("ProductService 클래스")
@ContextConfiguration(classes = ProductService.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Nested
    @DisplayName("findAllProducts 메소드는")
    class find_all_products_method {

        @Test
        @DisplayName("products가 하나도 없을경우, Empty List를 반환한다.")
        void return_empty_list_cannot_find_any_products() {
            // given
            when(productRepository.findAllProducts()).thenReturn(List.of());

            // when
            List<ProductResponse> result = productService.findAllProducts();

            // then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("존재하는 products를 ProductResponse list로 반환한다.")
        void return_product_response_list_exists_product() {
            // given
            Product product = new Product(1L, "product", "default-image", "1000");

            when(productRepository.findAllProducts()).thenReturn(List.of(product));

            List<ProductResponse> expected = List.of(
                    new ProductResponse(product.getId(), product.getName(), product.getImageUrl(), product.getPrice()));

            // when
            List<ProductResponse> result = productService.findAllProducts();

            // then
            assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        }

    }

}
