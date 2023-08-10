package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.domain.CartProduct;

@DisplayName("CartRepository 클래스")
@DataJpaTest
public class CartProductRepositoryTest {

    @Autowired
    private CartProductRepository cartProductRepository;

    @Nested
    @DisplayName("findByMemberIdAndProductId 메소드는")
    class FindByMemberIdAndProductId_Method {

        @Test
        @DisplayName("memberId와 productId에 해당하는 cartProduct 를 반환한다")
        void returnCartByMemberIdAndProductId() {
            // given
            Long memberId = 1L;
            Long productId = 1L;

            // when
            Optional<CartProduct> cartOptional = cartProductRepository.findOneByMemberIdAndProductId(
                memberId, productId);

            // then
            assertThat(cartOptional).isPresent();
        }
    }

}
