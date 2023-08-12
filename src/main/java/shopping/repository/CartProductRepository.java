package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shopping.domain.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    @Query("select c from CartProduct c join fetch c.product where c.member.id = ?1")
    List<CartProduct> findAllByMemberId(Long memberId);

    Optional<CartProduct> findOneByMemberIdAndProductId(Long memberId, Long ProductId);

    void deleteByMemberId(Long memberId);
}
