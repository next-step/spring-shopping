package shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.cart.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public Optional<Order> findByUserId(Long userId);
}
