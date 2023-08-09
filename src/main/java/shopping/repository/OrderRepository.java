package shopping.repository;

import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shopping.domain.Order;

@Repository
public class OrderRepository {

    private final EntityManager entityManager;

    public OrderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public long save(Order order) {
        entityManager.persist(order);
        return order.getId();
    }

    public Optional<Order> findById(long orderId) {
        return Optional.ofNullable(entityManager.find(Order.class, orderId));
    }

}
