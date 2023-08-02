package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.product.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem getByMemberAndProduct(Member member, Product product);

    boolean existsByMemberAndProduct(Member member, Product product);
}
