package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    @EntityGraph(attributePaths = "product")
    Optional<CartProduct> findByMemberIdAndProduct_Id(final Long memberId, final Long productId);

    @EntityGraph(attributePaths = "product")
    List<CartProduct> findAllByMemberId(final Long memberId);

    @EntityGraph(attributePaths = "product")
    Optional<CartProduct> findByIdAndMemberId(final Long cartProductId, final Long memberId);

    void deleteByIdAndMemberId(final Long cartProductId, final Long memberId);

    void deleteAllByMemberId(final Long memberId);
}
