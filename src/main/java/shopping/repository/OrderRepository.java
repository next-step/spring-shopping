package shopping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
