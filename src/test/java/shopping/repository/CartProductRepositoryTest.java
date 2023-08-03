package shopping.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.domain.CartProduct;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CartRepository 클래스")
@SpringBootTest
public class CartProductRepositoryTest {

    @Autowired
    private CartProductRepository cartRepository;

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
            Optional<CartProduct> cartOptional = cartRepository.findOneByMemberIdAndProductId(memberId, productId);

            // then
            assertThat(cartOptional).isPresent();
        }
    }

}
