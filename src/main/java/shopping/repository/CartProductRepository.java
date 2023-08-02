package shopping.repository;

import org.springframework.stereotype.Repository;
import shopping.domain.CartProduct;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class CartProductRepository {

    private final EntityManager entityManager;

    public CartProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(CartProduct cartProduct) {
        entityManager.persist(cartProduct);
    }

    public Optional<CartProduct> findByMemberIdAndProductId(Long memberId, Long productId) {
        return entityManager.createQuery("select c from CartProduct c where c.member.id = :memberId and c.product.id = :productId", CartProduct.class)
                .setParameter("productId", productId)
                .setParameter("memberId", memberId)
                .getResultList()
                .stream()
                .findAny();
    }
}
