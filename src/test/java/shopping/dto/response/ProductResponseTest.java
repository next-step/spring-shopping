package shopping.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.entity.Product;
import shopping.domain.vo.Image;
import shopping.domain.vo.Name;
import shopping.domain.vo.Price;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseTest {

    @Test
    @DisplayName("Product 엔티티로부터 Response DTO를 생성할 수 있다.")
    void createFromEntity() {
        // given
        Product product = new Product(1L, new Name("치킨"), new Image("chicken.png"), new Price(2000));

        // when
        ProductResponse response = ProductResponse.of(product);

        // then
        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName().getValue());
        assertThat(response.getImage()).isEqualTo(product.getImage().getValue());
        assertThat(response.getPrice()).isEqualTo(product.getPrice().getValue());
    }
}
