package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.cart.repository.CartProductRepository;
import shopping.cart.domain.CartProduct;
import shopping.product.domain.Product;
import shopping.product.repository.ProductRepository;

@DisplayName("장바구니 상품 Repository 테스트")
@DataJpaTest
class CartProductRepositoryTest {

    @Autowired
    CartProductRepository cartProductRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("MemberId와 ProductId로 장바구니 상품을 찾을 수 있다.")
    void findByMemberIdAndProductId() {
        /* given */
        final Long existMemberId = 1L;
        final Product existProduct = productRepository.findById(1L).orElseThrow();
        final Product notExistProduct = null;

        /* when */
        final Optional<CartProduct> exist = cartProductRepository.findByMemberIdAndProduct(
            existMemberId, existProduct);
        final Optional<CartProduct> notExist = cartProductRepository.findByMemberIdAndProduct(
            existMemberId, notExistProduct);

        /* then */
        assertThat(exist).isPresent();
        assertThat(notExist).isNotPresent();
    }

    @Test
    @DisplayName("Id와 MemberId로 장바구니 상품을 찾을 수 있다.")
    void findByIdAndMemberId() {
        /* given */
        final Long existCartProductId = 1L;
        final Long existMemberId = 1L;
        final Long notExistCartProductId = 1L;
        final Long notExistMemberId = 2L;

        /* when */
        final Optional<CartProduct> exist = cartProductRepository.findByIdAndMemberId(
            existCartProductId, existMemberId);
        final Optional<CartProduct> notExist = cartProductRepository.findByIdAndMemberId(
            notExistCartProductId, notExistMemberId);

        /* then */
        assertThat(exist).isPresent();
        assertThat(notExist).isNotPresent();
    }

    @Test
    @DisplayName("Id와 MemberId로 장바구니 상품을 제거할 수 있다. ")
    void deleteByIdAndMemberId() {
        /* given */
        final Long existCartProductId = 1L;
        final Long existMemberId = 1L;

        /* when */
        cartProductRepository.deleteByIdAndMemberId(existCartProductId, existMemberId);

        /* then */
        final Optional<CartProduct> originalCartProduct = cartProductRepository.findByIdAndMemberId(
            existCartProductId, existMemberId);
        assertThat(originalCartProduct).isNotPresent();
    }

    @Test
    @DisplayName("memberId로 장바구니 상품을 지운다.")
    void deleteAllByMemberId() {
        // given
        final Long existMemberId = 1L;
        // when
        cartProductRepository.deleteAllByMemberId(existMemberId);
        // then
        assertThat(cartProductRepository.findAllByMemberId(existMemberId).size())
            .isEqualTo(0);

    }
}
