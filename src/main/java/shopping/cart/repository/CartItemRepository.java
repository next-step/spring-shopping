package shopping.cart.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping.cart.domain.CartItem;
import shopping.cart.dto.ProductCartItemDto;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select new shopping.cart.dto.ProductCartItemDto(p, c) from CartItem c join Product p on p.id = c.productId where c.memberId = :memberId")
    List<ProductCartItemDto> findAllDtoByMemberId(@Param("memberId") Long memberId);

    Optional<CartItem> findCartItemByProductIdAndMemberId(Long productId, Long memberId);
}
