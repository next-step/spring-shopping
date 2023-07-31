package shopping.repository;

import org.springframework.stereotype.Repository;
import shopping.domain.Product;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ProductRepository {

    private final EntityManager entityManager;

    public ProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Product> findAll() {
        return entityManager.createQuery("select p from Product p", Product.class)
                .getResultList();
    }
}
