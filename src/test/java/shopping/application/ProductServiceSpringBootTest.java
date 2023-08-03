package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
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
class ProductServiceSpringBootTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @DisplayName("전체 상품 목록을 반환한다.")
    @Test
    void findAll() {
        // given
        Product chicken = new Product(1L, "치킨", "/chicken.jpg", 10_000L);
        Product pizza = new Product(2L, "피자", "/pizza.jpg", 20_000L);
        Product salad = new Product(3L, "샐러드", "/salad.jpg", 5_000L);
        productRepository.save(chicken);
        productRepository.save(pizza);
        productRepository.save(salad);

        // when
        List<ProductResponse> products = productService.findAll();

        // then
        assertThat(products).containsExactly(
                ProductResponse.of(chicken),
                ProductResponse.of(pizza),
                ProductResponse.of(salad)
        );
    }

    @DisplayName("전체 상품 목록의 페이지를 반환한다.")
    @Test
    void findAllByPage() {
        // given
        Product chicken = new Product(1L, "치킨", "/chicken.jpg", 10_000L);
        Product pizza = new Product(2L, "피자", "/pizza.jpg", 20_000L);
        Product salad = new Product(3L, "샐러드", "/salad.jpg", 5_000L);
        Product salad2 = new Product(4L, "샐러드2", "/salad.jpg", 5_000L);
        productRepository.save(chicken);
        productRepository.save(pizza);
        productRepository.save(salad);
        productRepository.save(salad2);

        // when
        Page<ProductResponse> products = productService.findAllByPage(1);

        // then
        assertThat(products.toList()).containsExactly(
                ProductResponse.of(chicken),
                ProductResponse.of(pizza),
                ProductResponse.of(salad),
                ProductResponse.of(salad2)
        );
    }
}

