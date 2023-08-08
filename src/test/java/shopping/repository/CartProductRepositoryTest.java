package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import shopping.domain.CartProduct;
import shopping.exception.CartException;

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
            CartProduct result = cartRepository.findOneByMemberIdAndProductId(memberId,
                productId);

            // then
            assertThat(result.getMember().getId()).isEqualTo(memberId);
            assertThat(result.getProduct().getId()).isEqualTo(productId);
        }

        @Test
        @DisplayName("memberId와 productId에 해당하는 cart 가 없으면 CartException 을 던진다")
        void returnOptionalEmptyByMemberIdAndProductId() {
            // given
            Long memberId = 5L;
            Long productId = 1L;

            // when & then
            assertThatThrownBy(() -> cartRepository.findOneByMemberIdAndProductId(memberId,
                productId)).isInstanceOf(CartException.class);
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
            CartProduct updated = cartRepository.findOneByMemberIdAndProductId(1L, 1L);
            assertThat(updated.getQuantity()).isEqualTo(updateQuantity);
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
            assertThatThrownBy(() -> cartRepository.findOneByMemberIdAndProductId(1L, 1L))
                .isInstanceOf(CartException.class);
        }
    }

    @Nested
    @Rollback
    @DisplayName("deleteByMemberId 메소드는")
    class DeleteByMemberId {

        @Test
        @DisplayName("member 의 모든 장바구니를 제거한다.")
        void deleteCartProductByMemberId() {
            // given
            Long memberId = 1L;

            // when
            cartRepository.deleteByMemberId(memberId);

            // then
            assertThat(cartRepository.findAllByMemberId(memberId)).isEmpty();
        }
    }
}
