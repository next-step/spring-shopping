package shopping.mart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shopping.auth.repository.entity.UserEntity;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Product;
import shopping.mart.repository.entity.ProductEntity;

@DisplayName("cartPersistService 클래스")
@ContextConfiguration(classes = {CartPersistService.class})
class CartPersistServiceTest extends JpaTest {

    @Autowired
    private CartPersistService cartPersistService;

    @Autowired
    private UserJpaTestSupportRepository userJpaTestSupportRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private CartJpaRepository cartJpaRepository;

    private UserEntity saveUser(String email, String password) {
        return userJpaTestSupportRepository.save(new UserEntity(null, email, password));
    }

    private ProductEntity saveProduct(String name, String imageUrl, String price) {
        return productJpaRepository.save(new ProductEntity(null, name, imageUrl, price));
    }

    @Nested
    @DisplayName("getByUserId 메소드는")
    class findByUserId_method {

        @Test
        @DisplayName("userId에 해당하는 Cart를 반환한다.")
        void return_cart_matched_user_id() {
            // given
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");
            Cart expected = cartPersistService.newCart(userEntity.getId());

            // when
            Cart result = cartPersistService.getByUserId(userEntity.getId());

            // then
            Assertions.assertCart(result, expected);
        }

        @Test
        @DisplayName("userId에 해당하는 Cart가 비어있으면, 비어있는 Cart를 반환한다.")
        void return_empty_cart_if_empty_cartproduct() {
            // given
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");
            saveProduct("product", "/images/default.png", "10000");

            Cart expected = cartPersistService.newCart(userEntity.getId());

            // when
            Cart result = cartPersistService.getByUserId(userEntity.getId());

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
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");

            Product product = PersistFixture.Product.withEntity(
                saveProduct("product", "/images/default.png", "10000"));

            Cart expected = cartPersistService.newCart(userEntity.getId());
            expected.addProduct(product);

            // when
            cartPersistService.persistCart(expected);
            Cart result = cartPersistService.getByUserId(userEntity.getId());

            // then
            Assertions.assertCart(result, expected);
        }

        @Test
        @DisplayName("기존에 업데이트 된 Cart를 받아, Cart의 상태를 영속성 저장소에 반영한다")
        void save_exist_cartProductEntity() {
            // given
            UserEntity userEntity = saveUser("hello@hello.world", "hello!123");

            Product product = PersistFixture.Product.withEntity(
                saveProduct("product1", "/images/default.png", "10000"));

            Cart expected = cartPersistService.newCart(userEntity.getId());
            expected.addProduct(product);
            cartPersistService.persistCart(expected);

            expected.updateProduct(product, 1000);

            // when
            cartPersistService.persistCart(expected);
            Cart result = cartPersistService.getByUserId(userEntity.getId());

            // then
            Assertions.assertCart(result, expected);
        }
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

}
