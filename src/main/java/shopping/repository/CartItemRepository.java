package shopping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.product.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem getByMemberAndProduct(Member member, Product product);

    boolean existsByMemberAndProduct(Member member, Product product);

    @EntityGraph(attributePaths = {"product"})
    List<CartItem> findAllByMemberId(Long memberId);
}
