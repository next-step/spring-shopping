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
        return entityManager.createQuery("select c from Cart c where c.member.id = :memberId and c.product.id = :productId", Cart.class)
                .setParameter("productId", productId)
                .setParameter("memberId", memberId)
                .getResultList()
                .stream()
                .findAny();
    }
}
