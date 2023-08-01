package shopping.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.domain.Cart;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CartRepository 클래스")
@SpringBootTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Nested
    @DisplayName("findByMemberIdAndProductId 메소드는")
    class FindByMemberIdAndProductId_Method {

        @Test
        @DisplayName("memberId와 productId에 해당하는 cart를 반환한다")
        void returnCartByMemberIdAndProductId() {
            // given
            Long memberId = 1L;
            Long productId = 1L;

            // when
            Optional<Cart> cartOptional = cartRepository.findByMemberIdAndProductId(memberId, productId);

            // then
            assertThat(cartOptional).isPresent();
        }
    }

}
