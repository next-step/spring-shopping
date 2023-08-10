package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.domain.cart.CartProduct;

@DisplayName("장바구니 상품 Repository 테스트")
@DataJpaTest
class CartProductRepositoryTest {

    @Autowired
    CartProductRepository cartProductRepository;

    @Test
    @DisplayName("MemberId와 ProductId로 장바구니 상품을 찾을 수 있다.")
    void findByMemberIdAndProductId() {
        /* given */
        final Long existMemberId = 1L;
        final Long existProductId = 1L;
        final Long notExistMemberId = 123L;
        final Long notExistProductId = 123L;

        /* when */
        final Optional<CartProduct> exist = cartProductRepository
            .findByMemberIdAndProduct_Id(existMemberId, existProductId);
        final Optional<CartProduct> notExist = cartProductRepository.
            findByMemberIdAndProduct_Id(notExistMemberId, notExistProductId);

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
        final Optional<CartProduct> exist = cartProductRepository
            .findByIdAndMemberId(existCartProductId, existMemberId);
        final Optional<CartProduct> notExist = cartProductRepository
            .findByIdAndMemberId(notExistCartProductId, notExistMemberId);

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
        final Optional<CartProduct> originalCartProduct = cartProductRepository
            .findByMemberIdAndProduct_Id(existMemberId, existCartProductId);
        assertThat(originalCartProduct).isNotPresent();
    }
}
