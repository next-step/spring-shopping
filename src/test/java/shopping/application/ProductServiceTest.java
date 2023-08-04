package shopping.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.domain.cart.Product;
import shopping.dto.response.ProductResponse;
import shopping.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 서비스 통합 테스트")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @DisplayName("전체 상품 목록을 반환한다.")
    @Test
    void findAll() {
        // given
        Product chicken = new Product("치킨", "/chicken.jpg", 10_000L);
        Product pizza = new Product("피자", "/pizza.jpg", 20_000L);
        Product salad = new Product("샐러드", "/salad.jpg", 5_000L);
        List<Product> productList = List.of(chicken, pizza, salad);
        productRepository.saveAll(productList);

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