package shopping.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopping.order.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
