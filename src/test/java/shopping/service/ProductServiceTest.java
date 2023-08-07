package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.when;
import static shopping.fixture.ProductFixture.CHICKEN_IMAGE;
import static shopping.fixture.ProductFixture.CHICKEN_NAME;
import static shopping.fixture.ProductFixture.CHICKEN_PRICE;
import static shopping.fixture.ProductFixture.PIZZA_IMAGE;
import static shopping.fixture.ProductFixture.PIZZA_NAME;
import static shopping.fixture.ProductFixture.PIZZA_PRICE;
import static shopping.fixture.ProductFixture.createChicken;
import static shopping.fixture.ProductFixture.createPizza;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.domain.product.Product;
import shopping.dto.response.ProductResponses;
import shopping.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductService.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 목록을 읽어들이는 테스트")
    void readProducts() {
        List<Product> products = List.of(createChicken(), createPizza());
        when(productRepository.findAll()).thenReturn(products);

        final ProductResponses productResponses = productService.readProducts();

        assertThat(productResponses.getProducts())
                .extracting("id", "image", "name", "price")
                .contains(
                        tuple(1L, CHICKEN_IMAGE, CHICKEN_NAME, CHICKEN_PRICE),
                        tuple(2L, PIZZA_IMAGE, PIZZA_NAME, PIZZA_PRICE)
                );
    }
}
