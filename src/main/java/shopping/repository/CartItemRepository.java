package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(attributePaths = {"product", "member"})
    Optional<CartItem> findOneWithProductAndMemberById(final Long cartItemId);

    @EntityGraph(attributePaths = {"product"})
    List<CartItem> findAllByMemberId(final Long memberId);
}
