package shopping.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shopping.domain.OrderProduct;

@Repository
public class OrderProductRepository {

    private final EntityManager entityManager;

    public OrderProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveAll(List<OrderProduct> orderProducts) {
        orderProducts.forEach(entityManager::persist);
    }
}
