package shopping.cart.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.cart.domain.CartProduct;
import shopping.product.domain.Product;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    Optional<CartProduct> findByIdAndMemberId(final Long cartProductId, final Long memberId);

    Optional<CartProduct> findByMemberIdAndProduct(final Long memberId, final Product product);

    void deleteByIdAndMemberId(final Long cartProductId, final Long memberId);

    void deleteAllByMemberId(final Long memberId);

    List<CartProduct> findAllByMemberId(final Long memberId);
}
