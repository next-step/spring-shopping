package shopping.repository;

import org.springframework.stereotype.Repository;
import shopping.domain.Cart;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class CartRepository {

    private final EntityManager entityManager;

    public CartRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Cart cart) {
        entityManager.persist(cart);
    }

    public Optional<Cart> findByMemberIdAndProductId(Long memberId, Long productId) {
        return null;
    }
}
