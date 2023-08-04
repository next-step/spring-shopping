package shopping.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.entity.product.Image;
import shopping.entity.product.Name;
import shopping.entity.product.Price;
import shopping.entity.product.Product;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseTest {

    @Test
    @DisplayName("Product 엔티티로부터 Response DTO를 생성할 수 있다.")
    public void createFromEntity() {
        // given
        Product product = new Product(1L, new Name("치킨"), new Image("chicken.png"), new Price(2000));

        // when
        ProductResponse response = ProductResponse.of(product);

        // then
        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getImage()).isEqualTo(product.getImage());
        assertThat(response.getPrice()).isEqualTo(product.getPrice());
    }
}
