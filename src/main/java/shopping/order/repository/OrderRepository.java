package shopping.order.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.order.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByMemberIdAndId(Long memberId, Long orderId);

    List<Order> findByMemberId(Long memberId);
}
