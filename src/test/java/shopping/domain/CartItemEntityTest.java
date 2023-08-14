package shopping.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.entity.CartItemEntity;
import shopping.domain.entity.ProductEntity;
import shopping.domain.entity.UserEntity;

@DisplayName("CartItemEntityTest")
public class CartItemEntityTest {

    @Test
    @DisplayName("성공 : 장바구니에 담긴 아이템의 가격과 갯수를 통해 해당 상품의 총 가격을 리턴")
    void calculatePrice() {
        // given
        UserEntity user = new UserEntity(1L, "test@email.com", "test_password");
        ProductEntity pizza = new ProductEntity(2L, "피자", "image_pizza", 2500);
        CartItemEntity cartPizza = new CartItemEntity(2L, user, pizza, 2);

        // when
        int totalPrice = cartPizza.calculatePrice();

        // then
        Assertions.assertThat(totalPrice).isEqualTo(cartPizza.getQuantity() * pizza.getPrice());
    }

}
