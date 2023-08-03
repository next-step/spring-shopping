package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    Optional<CartProduct> findByMemberIdAndProductId(final Long memberId, final Long productId);

    List<CartProduct> findByMemberId(final Long memberId);
}
