package shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.Email;
import shopping.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Page<Order> findAllByUserEmail(Email email, Pageable pageable);

}
