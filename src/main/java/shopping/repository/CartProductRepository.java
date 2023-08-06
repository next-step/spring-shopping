package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.domain.cart.CartProduct;
import shopping.dto.response.CartResponse;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    Optional<CartProduct> findByMemberIdAndProductId(final Long memberId, final Long productId);

    Optional<CartProduct> findByIdAndMemberId(final Long cartProductId, final Long memberId);

    void deleteByIdAndMemberId(final Long cartProductId, final Long memberId);

    @Query("select new shopping.dto.response.CartResponse(c,p) from Product p join CartProduct c on c.productId = p.id")
    List<CartResponse> findAllByMemberId(final Long memberId);
}
