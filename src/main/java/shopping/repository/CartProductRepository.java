package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    Optional<CartProduct> findByMemberIdAndProductId(final Long memberId, final Long productId);

    List<CartProduct> findAllByMemberId(final Long memberId);

    Optional<CartProduct> findByIdAndMemberId(final Long cartProductId, final Long memberId);

    void deleteByIdAndMemberId(final Long cartProductId, final Long memberId);
}
