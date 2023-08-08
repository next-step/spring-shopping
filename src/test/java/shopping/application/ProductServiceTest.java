package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Product;
import shopping.dto.response.ProductResponse;
import shopping.repository.ProductRepository;

@Transactional
@SpringBootTest
@DisplayName("상품 테스트 통합 테스트")
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @DisplayName("정상적으로 전체 상품 목록의 페이지를 반환한다.")
    @Test
    void findAllByPage() {
        // given
        Product chicken = new Product(1L, "치킨", "/chicken.jpg", 10_000L);
        Product pizza = new Product(2L, "피자", "/pizza.jpg", 20_000L);
        Product salad = new Product(3L, "샐러드", "/salad.jpg", 5_000L);
        Product salad2 = new Product(4L, "샐러드2", "/salad.jpg", 5_000L);
        Product chickenResult = productRepository.save(chicken);
        Product pizzaResult = productRepository.save(pizza);
        Product saladResult = productRepository.save(salad);
        Product salad2Result = productRepository.save(salad2);

        // when
        Page<ProductResponse> products = productService.findAllByPage(1, 12);

        // then
        assertThat(products.toList()).containsExactly(
                ProductResponse.of(chickenResult),
                ProductResponse.of(pizzaResult),
                ProductResponse.of(saladResult),
                ProductResponse.of(salad2Result)
        );
    }

    @DisplayName("페이지 입력값이 양수가 아닐때 전체 상품 목록의 1페이지를 반환한다.")
    @Test
    void findAllByPageNotPositiveThenReturn1Page() {
        // given
        Product chicken = new Product(1L, "치킨", "/chicken.jpg", 10_000L);
        Product pizza = new Product(2L, "피자", "/pizza.jpg", 20_000L);
        Product salad = new Product(3L, "샐러드", "/salad.jpg", 5_000L);
        Product chickenResult = productRepository.save(chicken);
        Product pizzaResult = productRepository.save(pizza);
        Product saladResult = productRepository.save(salad);

        // when
        Page<ProductResponse> products = productService.findAllByPage(-1, 12);

        // then
        assertThat(products.toList()).containsExactly(
                ProductResponse.of(chickenResult),
                ProductResponse.of(pizzaResult),
                ProductResponse.of(saladResult)
        );
    }
}

