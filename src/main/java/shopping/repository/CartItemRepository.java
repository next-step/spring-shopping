package shopping.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.product.Product;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllByMember(final Member member);

    CartItem getByMemberAndProduct(final Member member, final Product product);

    boolean existsByMemberAndProduct(final Member member, final Product product);

    @EntityGraph(attributePaths = {"product"})
    List<CartItem> findAllByMemberId(final Long memberId);
}
