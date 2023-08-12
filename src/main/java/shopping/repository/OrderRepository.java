package shopping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMemberId(Long memberId);
}
