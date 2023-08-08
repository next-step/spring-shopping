package shopping.repository;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shopping.domain.Order;

@Repository
public class OrderRepository {

    private final EntityManager entityManager;

    public OrderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Order order) {
        entityManager.persist(order);
    }
}
