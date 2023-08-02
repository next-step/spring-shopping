package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.domain.cart.CartProduct;

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
        final Optional<CartProduct> exist = cartProductRepository.findByMemberIdAndProductId(
            existMemberId, existProductId);
        final Optional<CartProduct> notExist = cartProductRepository.findByMemberIdAndProductId(
            notExistMemberId, notExistProductId);

        /* then */
        assertThat(exist).isPresent();
        assertThat(notExist).isNotPresent();
    }
}
