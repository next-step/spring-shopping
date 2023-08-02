package shopping.mart.persist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shopping.core.entity.CartEntity;
import shopping.core.entity.CartProductEntity;
import shopping.core.entity.ProductEntity;
import shopping.core.entity.UserEntity;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Product;
import shopping.mart.persist.repository.CartProductJpaRepository;
import shopping.mart.persist.repository.ProductJpaRepository;

@DisplayName("CartRepository 클래스")
@ContextConfiguration(classes = {CartRepository.class})
class CartRepositoryTest extends JpaTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserJpaTestSupportRepository userJpaTestSupportRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private CartProductJpaRepository cartProductJpaRepository;

    private UserEntity saveUser(String email, String password) {
        return userJpaTestSupportRepository.saveAndFlush(new UserEntity(null, email, password));
    }

    private ProductEntity saveProduct(String name, String imageUrl, String price) {
        return productJpaRepository.saveAndFlush(new ProductEntity(null, name, imageUrl, price));
    }

    private void saveCartProduct(Cart cart, ProductEntity productEntity) {
        cartProductJpaRepository.save(
                new CartProductEntity(null, new CartEntity(cart.getCartId(), cart.getUserId()), productEntity, 1));
    }

    private void assertCart(Cart result, Map<Product, Integer> expected) {
        assertThat(result.getCartId()).isNotNull();
        assertThat(result.getProductCounts()).hasSize(expected.size()).isEqualTo(expected);
    }

    private Product productEntityToDomain(ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(),
                productEntity.getPrice());
    }

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
            saveProduct("product", "/images/default.png", "10000");

            cartRepository.newCart(userEntity.getId());

            Map<Product, Integer> expected = Map.of();

            // when
            Cart result = cartRepository.getByUserId(userEntity.getId());

            // then
            assertCart(result, expected);
        }
    }

    @Nested
    @DisplayName("updateCart 메소드는")
    class updateCart_method {

        @Test
        @DisplayName("cart에 저장된 product의 수량을 업데이트한다.")
        void update_product_count_saved_in_cart() {
            // given
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");
            ProductEntity productEntity = saveProduct("product", "/images/default.png", "10000");

            Cart cart = cartRepository.newCart(userEntity.getId());

            saveCartProduct(cart, productEntity);
            cart = cartRepository.getByUserId(userEntity.getId());

            Product product = extractFirstProduct(cart);

            int expectedCount = 123123;
            cart.getProductCounts().put(product, expectedCount);
            Map<Product, Integer> expected = Map.of(product, expectedCount);

            // when
            cartRepository.updateCart(cart);
            Cart result = cartRepository.getByUserId(userEntity.getId());

            // then
            assertCart(result, expected);
        }

        @Test
        @DisplayName("cart에 저장된 product가 0이되면, 삭제한다.")
        void delete_product_if_count_zero() {
            // given
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");
            ProductEntity productEntity = saveProduct("product", "/images/default.png", "10000");

            Cart cart = cartRepository.newCart(userEntity.getId());

            saveCartProduct(cart, productEntity);
            cart = cartRepository.getByUserId(userEntity.getId());

            Product product = extractFirstProduct(cart);

            cart.getProductCounts().put(product, 0);
            Map<Product, Integer> expected = Map.of();

            // when
            cartRepository.updateCart(cart);
            Cart result = cartRepository.getByUserId(userEntity.getId());

            // then
            assertCart(result, expected);
        }

        @Test
        @DisplayName("cart에 저장된 product가 삭제되면, entity에서도 삭제된다.")
        void delete_product_if_domain_product_deleted() {
            // given
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");
            ProductEntity productEntity = saveProduct("product", "/images/default.png", "10000");

            Cart cart = cartRepository.newCart(userEntity.getId());

            saveCartProduct(cart, productEntity);
            cart = cartRepository.getByUserId(userEntity.getId());

            Product product = extractFirstProduct(cart);

            cart.deleteProduct(product);
            Map<Product, Integer> expected = Map.of();

            // when
            cartRepository.updateCart(cart);
            Cart result = cartRepository.getByUserId(userEntity.getId());

            // then
            assertCart(result, expected);
        }

        private Product extractFirstProduct(Cart cart) {
            return cart.getProductCounts().entrySet().stream()
                    .findFirst()
                    .orElseThrow(IllegalStateException::new).getKey();
        }
    }

    @Nested
    @DisplayName("addProduct 메소드는")
    class addProduct_method {

        @Test
        @DisplayName("cart와 product를 받아, 새로운 CartProductEntity를 저장한다")
        void save_new_cartProductEntity() {
            // given
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");

            Product product = PersistFixture.Product.withEntity(saveProduct("product", "/images/default.png", "10000"));

            Cart cart = cartRepository.newCart(userEntity.getId());

            cartRepository.addProduct(cart, product);
            Map<Product, Integer> expected = Map.of(product, 1);

            // when
            Cart result = cartRepository.getByUserId(userEntity.getId());

            // then
            assertCart(result, expected);
        }
    }
}
