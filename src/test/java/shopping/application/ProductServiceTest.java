package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import shopping.domain.cart.Product;
import shopping.dto.response.ProductResponse;
import shopping.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 서비스 통합 테스트")
class ProductServiceTest extends ServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("전체 상품 목록 조회")
    @Test
    void findAll() {
        // given
        List<Product> productList = List.of(
                new Product("치킨", "/chicken.jpg", 10_000L),
                new Product("피자", "/pizza.jpg", 20_000L),
                new Product("샐러드", "/salad.jpg", 5_000L)
        );
        List<Product> savedProducts = productRepository.saveAll(productList);

        // when
        List<ProductResponse> products = productService.findAll(PageRequest.of(0, 3));

        // then
        assertThat(products).usingRecursiveComparison().isEqualTo(
                savedProducts.stream()
                        .map(ProductResponse::of)
                        .collect(Collectors.toList())
        );
    }
}