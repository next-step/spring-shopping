package shopping.order.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMemberId(Long memberId);
}
