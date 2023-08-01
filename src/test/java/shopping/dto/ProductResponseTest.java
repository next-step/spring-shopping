package shopping.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseTest {

    @Test
    @DisplayName("Product 엔티티로부터 Response DTO를 생성할 수 있다.")
    public void createFromEntity() {
        // given
        final Product product = new Product(1L, "치킨", "/chicken.png", 10000);

        // when
        ProductResponse response = ProductResponse.of(product);

        // then
        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getImage()).isEqualTo(product.getImage());
        assertThat(response.getPrice()).isEqualTo(product.getPrice());
    }
}