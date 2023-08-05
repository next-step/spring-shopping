package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
