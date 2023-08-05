package shopping.mart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.mart.domain.Product;
import shopping.mart.domain.exception.AlreadyExistProductException;
import shopping.mart.dto.ProductCreateRequest;
import shopping.mart.persist.ProductPersistService;

@ExtendWith(SpringExtension.class)
@DisplayName("ProductService 클래스")
@ContextConfiguration(classes = {ProductService.class})
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductPersistService productRepository;

    @Nested
    @DisplayName("saveProduct 메서드는")
    class saveProduct_method {

        @Test
        @DisplayName("이미 저장되어 있는 데이터라면 AlreadyExistProductException을 던진다.")
        void throw_AlreadyExistProductException_when_exist_data() {
            // given
            ProductCreateRequest productCreateRequest = new ProductCreateRequest(
                "치킨", "/default-product.png", "30000");

            Product existProduct = new Product(1L, "치킨", "/default-product.png", "30000");
            when(productRepository.findByProductName(anyString())).thenReturn(
                Optional.of(existProduct));

            // when
            Exception exception = catchException(
                () -> productService.saveProduct(productCreateRequest));

            // then
            assertThat(exception).isInstanceOf(AlreadyExistProductException.class);
        }
    }
}
