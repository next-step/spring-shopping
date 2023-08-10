package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopping.domain.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderProducts where o.memberId = ?1 and o.id = ?2")
    Optional<Order> findByIdAndMemberIdWithOrderProduct(final Long orderId, final Long memberId);

    List<Order> findAllByMemberId(final Long memberId);
}
