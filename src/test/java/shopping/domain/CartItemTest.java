package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shopping.domain.entity.CartItemEntity;
import shopping.domain.entity.ProductEntity;
import shopping.domain.entity.UserEntity;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@DisplayName("CartItemTest")
public class CartItemTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 500, 1000})
    @DisplayName("성공 : 수량이 1 ~ 1000개인 CartItem 객체 생성")
    void createCartItemOne(int quantity) {
        // given
        UserEntity userEntity = new UserEntity(1L, "test@test.com", "test_password");
        ProductEntity productEntity = new ProductEntity(1L, "테스트_상품_이름", "상품_이미지", 1000);
        CartItemEntity cartItemEntity = new CartItemEntity(1L, userEntity, productEntity, quantity);

        // then
        assertThatNoException()
            .isThrownBy(() -> CartItem.from(cartItemEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1001})
    @DisplayName("예외 : 수량이 0개 미만 1000개 초과인 경우 예외 발생")
    void exceptionCartItemQuantity(int quantity) {
        // given
        UserEntity userEntity = new UserEntity(1L, "test@test.com", "test_password");
        ProductEntity productEntity = new ProductEntity(1L, "테스트_상품_이름", "상품_이미지", 1000);
        CartItemEntity cartItemEntity = new CartItemEntity(1L, userEntity, productEntity, quantity);

        // then
        assertThatThrownBy(() -> CartItem.from(cartItemEntity))
            .isInstanceOf(ShoppingException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.INVALID_CART_ITEM_QUANTITY);
    }

    @Test
    @DisplayName("성공 : 장바구니에 상품을 등록한 유저와 수정하려는 유저의 아이디가 동일한 경우")
    void sameUser() {
        // given
        UserEntity userEntity = new UserEntity(1L, "test@test.com", "test_password");
        ProductEntity productEntity = new ProductEntity(1L, "테스트_상품_이름", "상품_이미지", 1000);
        CartItemEntity cartItemEntity = new CartItemEntity(1L, userEntity, productEntity, 1);
        CartItem cartItem = CartItem.from(cartItemEntity);

        // when
        Long userId = 1L;

        // then
        assertThatNoException()
            .isThrownBy(() -> cartItem.matchUser(userId));
    }

    @Test
    @DisplayName("예외: 장바구니에 상품을 등록한 유저와 수정하려는 유저의 아이디가 다를 경우 예외 발생")
    void exceptionNotSameUser() {
        // given
        UserEntity userEntity = new UserEntity(1L, "test@test.com", "test_password");
        ProductEntity productEntity = new ProductEntity(1L, "테스트_상품_이름", "상품_이미지", 1000);
        CartItemEntity cartItemEntity = new CartItemEntity(1L, userEntity, productEntity, 1);
        CartItem cartItem = CartItem.from(cartItemEntity);

        // when
        Long userId = 2L;

        // then
        assertThatThrownBy(() -> cartItem.matchUser(userId))
            .isInstanceOf(ShoppingException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.INVALID_CART_ITEM);
    }

    @Test
    @DisplayName("true : 장바구니의 상품 수량이 0개인 경우 true 반환")
    void isQuantityZero() {
        // given
        UserEntity userEntity = new UserEntity(1L, "test@test.com", "test_password");
        ProductEntity productEntity = new ProductEntity(1L, "테스트_상품_이름", "상품_이미지", 1000);
        CartItemEntity cartItemEntity = new CartItemEntity(1L, userEntity, productEntity, 0);
        CartItem cartItem = CartItem.from(cartItemEntity);

        // then
        assertThat(cartItem.isQuantityZero()).isTrue();
    }

}
