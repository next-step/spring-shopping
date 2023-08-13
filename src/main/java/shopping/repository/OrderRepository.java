package shopping.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.domain.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "orderProducts")
    Optional<Order> findByIdAndMemberId(final Long orderId, final Long memberId);

    @EntityGraph(attributePaths = "orderProducts")
    List<Order> findAllByMemberId(final Long memberId);
}
