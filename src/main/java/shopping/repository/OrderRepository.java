package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"orderItems"})
    Optional<Order> findOrderWithOrderItemsById(final Long orderId);

    @EntityGraph(attributePaths = {"orderItems"})
    List<Order> findAllByMemberId(final Long memberId);
}
