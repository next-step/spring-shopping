package shopping.order.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderItems where o.id = :id")
    Optional<Order> findOrderByIdWithFetchJoinOrderItem(@Param("id") Long id);

    @Query("select o from Order o join fetch o.orderItems where o.memberId = :memberId")
    List<Order> findAllOrderByMemberId(@Param("memberId") Long memberId);
}
