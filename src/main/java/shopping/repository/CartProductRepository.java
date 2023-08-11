package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopping.domain.cart.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    Optional<CartProduct> findByMemberIdAndProduct_Id(final Long memberId, final Long productId);

    @Query("select cp from CartProduct cp join fetch cp.product where cp.memberId = ?1")
    List<CartProduct> findAllByMemberId(final Long memberId);

    Optional<CartProduct> findByIdAndMemberId(final Long cartProductId, final Long memberId);

    void deleteByIdAndMemberId(final Long cartProductId, final Long memberId);

    void deleteAllByMemberId(final Long memberId);
}
