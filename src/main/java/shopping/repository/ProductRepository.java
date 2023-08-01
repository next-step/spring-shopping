package shopping.repository;

import org.springframework.stereotype.Repository;
import shopping.domain.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }
}
