package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.product.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(attributePaths = {"product", "member"})
    Optional<CartItem> findById(final Long cartItemId);

    CartItem getByMemberAndProduct(final Member member, final Product product);

    boolean existsByMemberAndProduct(final Member member, final Product product);

    @EntityGraph(attributePaths = {"product"})
    List<CartItem> findAllByMemberId(final Long memberId);
}
