package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static shopping.application.support.DomainFixture.getProduct;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.Product;
import shopping.dto.ProductResponse;
import shopping.repository.ProductRepository;

@DisplayName("ProductService 클래스")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Nested
    @DisplayName("findAll 메소드는")
    class FindAll_Method {

        @Test
        @DisplayName("저장된 모든 Product 를 가져온다")
        void find_all_products() {
            // given
            Product product1 = getProduct(1L);
            Product product2 = getProduct(2L);

            given(productRepository.findAll()).willReturn(List.of(product1, product2));

            // when
            List<ProductResponse> result = productService.findAll();

            // then
            assertThat(result).extracting("name").containsExactlyInAnyOrder("치킨", "피자");

        }
    }
}