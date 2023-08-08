package shopping.mart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shopping.mart.app.domain.Cart;
import shopping.mart.app.domain.Product;
import shopping.mart.repository.entity.ProductEntity;

@DisplayName("cartPersistService 클래스")
@ContextConfiguration(classes = {CartPersistService.class})
class CartPersistServiceTest extends JpaTest {

    @Autowired
    private CartPersistService cartPersistService;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private CartJpaRepository cartJpaRepository;

    private ProductEntity saveProduct(String name, String imageUrl, String price) {
        return productJpaRepository.save(new ProductEntity(null, name, imageUrl, price));
    }

    static final class Assertions {

        static void assertCart(Cart result, Cart expected) {
            SoftAssertions.assertSoftly(softAssertions -> {
                assertThat(result.getCartId()).isEqualTo(expected.getCartId());
                assertThat(result.getUserId()).isEqualTo(expected.getUserId());
                assertThat(result.getProductCounts()).isEqualTo(expected.getProductCounts());
            });
        }
    }

    @Nested
    @DisplayName("getByUserId 메소드는")
    class findByUserId_method {

        @Test
        @DisplayName("userId에 해당하는 Cart를 반환한다.")
        void return_cart_matched_user_id() {
            // given
            long userId = 1L;
            Cart expected = cartPersistService.newCart(1L);

            // when
            Cart result = cartPersistService.getByUserId(userId);

            // then
            Assertions.assertCart(result, expected);
        }

        @Test
        @DisplayName("userId에 해당하는 Cart가 비어있으면, 비어있는 Cart를 반환한다.")
        void return_empty_cart_if_empty_cartproduct() {
            // given
            long userId = 1L;
            saveProduct("product", "/images/default.png", "10000");

            Cart expected = cartPersistService.newCart(userId);

            // when
            Cart result = cartPersistService.getByUserId(userId);

            // then
            Assertions.assertCart(result, expected);
        }
    }

    @Nested
    @DisplayName("persistCart 메소드는")
    class addProduct_method {

        @Test
        @DisplayName("처음으로 업데이트 된 Cart를 받아, Cart의 상태를 영속성 저장소에 반영한다")
        void save_new_cartProductEntity() {
            // given
            long userId = 1L;

            Product product = saveProduct("product", "/images/default.png", "10000").toDomain();

            Cart expected = cartPersistService.newCart(userId);
            expected.addProduct(product);

            // when
            cartPersistService.persistCart(expected);
            Cart result = cartPersistService.getByUserId(userId);

            // then
            Assertions.assertCart(result, expected);
        }

        @Test
        @DisplayName("기존에 업데이트 된 Cart를 받아, Cart의 상태를 영속성 저장소에 반영한다")
        void save_exist_cartProductEntity() {
            // given
            long userId = 1L;

            Product product = saveProduct("product1", "/images/default.png", "10000").toDomain();

            Cart expected = cartPersistService.newCart(userId);
            expected.addProduct(product);
            cartPersistService.persistCart(expected);

            expected.updateProduct(product, 1000);

            // when
            cartPersistService.persistCart(expected);
            Cart result = cartPersistService.getByUserId(userId);

            // then
            Assertions.assertCart(result, expected);
        }
    }

}
