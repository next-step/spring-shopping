package shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import shopping.domain.Email;
import shopping.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @EntityGraph(attributePaths = {"orderItems"})
    Page<Order> findAllByUserEmail(@Param("email") Email email, Pageable pageable);

}
