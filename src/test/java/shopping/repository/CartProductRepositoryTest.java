package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import shopping.domain.CartProduct;

@DisplayName("CartRepository 클래스")
@DataJpaTest
@Import(value = {CartProductRepository.class})
public class CartProductRepositoryTest {

    @Autowired
    private CartProductRepository cartRepository;

    @Nested
    @DisplayName("findAllByMemberId 메소드는")
    class FindAllByMemberId {

        @Test
        @DisplayName("memberId에 해당하는 CartProduct 를 반환한다.")
        void returnCartByMemberIdAndProductId() {
            // given
            Long memberId = 1L;

            // when
            List<CartProduct> result = cartRepository.findAllByMemberId(memberId);

            // then
            assertThat(result).hasSize(3);
        }
    }

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
            Optional<CartProduct> result = cartRepository.findOneByMemberIdAndProductId(memberId,
                productId);

            // then
            assertThat(result).isPresent();
        }

        @Test
        @DisplayName("memberId와 productId에 해당하는 cart 가 없으면 Optional.empty 를 반환한다")
        void returnOptionalEmptyByMemberIdAndProductId() {
            // given
            Long memberId = 5L;
            Long productId = 1L;

            // when
            Optional<CartProduct> result = cartRepository.findOneByMemberIdAndProductId(memberId,
                productId);

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("updateById 메소드는")
    class UpdateById {

        @Test
        @DisplayName("id 에 해당하는 CartProduct 의 수량을 갱신한다.")
        void updateQuantityById() {
            // given
            Long id = 1L;
            int updateQuantity = 10;

            // when
            cartRepository.updateById(id, updateQuantity);

            // then
            Optional<CartProduct> updated = cartRepository.findOneByMemberIdAndProductId(1L, 1L);
            assertThat(updated.get().getQuantity()).isEqualTo(updateQuantity);
        }
    }

    @Nested
    @Rollback
    @DisplayName("deleteById 메소드는")
    class DeleteById {

        @Test
        @DisplayName("id 에 해당하는 CartProduct 을 제거한다.")
        void deleteCartProductById() {
            // given
            Long id = 1L;

            // when
            cartRepository.deleteById(id);

            // then
            Optional<CartProduct> deleted = cartRepository.findOneByMemberIdAndProductId(1L, 1L);
            assertThat(deleted).isNotPresent();
        }
    }
}
