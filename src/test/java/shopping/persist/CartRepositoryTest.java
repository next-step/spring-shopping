package shopping.persist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shopping.domain.Cart;
import shopping.domain.Product;
import shopping.persist.entity.CartEntity;
import shopping.persist.entity.CartProductEntity;
import shopping.persist.entity.ProductEntity;
import shopping.persist.entity.UserEntity;
import shopping.persist.repository.CartProductJpaRepository;
import shopping.persist.repository.ProductJpaRepository;
import shopping.persist.repository.UserJpaRepository;

@DisplayName("CartRepository 클래스")
@ContextConfiguration(classes = {CartRepository.class})
class CartRepositoryTest extends JpaTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private CartProductJpaRepository cartProductJpaRepository;

    @Nested
    @DisplayName("getByUserId 메소드는")
    class findByUserId_method {

        @Test
        @DisplayName("userId에 해당하는 Cart를 반환한다.")
        void return_cart_matched_user_id() {
            // given
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");
            ProductEntity productEntity = saveProduct("product", "/images/default.png", "10000");

            Cart cart = cartRepository.newCart(userEntity.getId());

            saveCartProduct(cart, productEntity);

            Map<Product, Integer> expected = Map.of(productEntityToDomain(productEntity), 1);

            // when
            Cart result = cartRepository.getByUserId(userEntity.getId());

            // then
            assertCart(result, expected);
        }

        @Test
        @DisplayName("userId에 해당하는 Cart가 비어있으면, 비어있는 Cart를 반환한다.")
        void return_empty_cart_if_empty_cartproduct() {
            // given
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");
            ProductEntity productEntity = saveProduct("product", "/images/default.png", "10000");

            cartRepository.newCart(userEntity.getId());

            Map<Product, Integer> expected = Map.of();

            // when
            Cart result = cartRepository.getByUserId(userEntity.getId());

            // then
            assertCart(result, expected);
        }

        private UserEntity saveUser(String email, String password) {
            return userJpaRepository.saveAndFlush(new UserEntity(null, email, password));
        }

        private ProductEntity saveProduct(String name, String imageUrl, String price) {
            return productJpaRepository.saveAndFlush(new ProductEntity(null, name, imageUrl, price));
        }

        private void saveCartProduct(Cart cart, ProductEntity productEntity) {
            cartProductJpaRepository.save(
                    new CartProductEntity(null, new CartEntity(cart.getCartId(), cart.getUserId()), productEntity));
        }

        private void assertCart(Cart result, Map<Product, Integer> expected) {
            assertThat(result.getCartId()).isNotNull();
            assertThat(result.getProductCounts()).hasSize(expected.size()).isEqualTo(expected);
        }

        private Product productEntityToDomain(ProductEntity productEntity) {
            return new Product(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(),
                    productEntity.getPrice());
        }
    }

}
