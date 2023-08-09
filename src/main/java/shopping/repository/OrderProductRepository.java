package shopping.repository;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class OrderProductRepository {

    private final EntityManager entityManager;

    public OrderProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
