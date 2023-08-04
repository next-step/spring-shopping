package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.cart.Product;
import shopping.dto.response.ProductResponse;
import shopping.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("전체 상품 목록을 반환한다.")
    @Test
    void findAll() {
        // given
        Product chicken = new Product(1L, "치킨", "/chicken.jpg", 10_000L);
        Product pizza = new Product(2L, "피자", "/pizza.jpg", 20_000L);
        Product salad = new Product(3L, "샐러드", "/salad.jpg", 5_000L);
        List<Product> productList = List.of(chicken, pizza, salad);

        Mockito.when(productRepository.findAll()).thenReturn(productList);

        // when
        List<ProductResponse> products = productService.findAll();

        // then
        assertThat(products).containsExactly(
                ProductResponse.of(chicken),
                ProductResponse.of(pizza),
                ProductResponse.of(salad)
        );
    }
}
