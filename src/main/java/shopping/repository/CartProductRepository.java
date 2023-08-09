package shopping.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import shopping.domain.CartProduct;
import shopping.exception.CartException;

@Repository
public class CartProductRepository {

    private final EntityManager entityManager;

    public CartProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(CartProduct cartProduct) {
        entityManager.persist(cartProduct);
    }

    public List<CartProduct> findAllByMemberId(Long memberId) {
        return entityManager.createQuery(
                "select c from CartProduct c join fetch c.product p where c.member.id = :memberId",
                CartProduct.class)
            .setParameter("memberId", memberId)
            .getResultList();
    }

    public CartProduct findOneByMemberIdAndProductId(Long memberId, Long productId) {
        try {
            return entityManager.createQuery(
                    "select c from CartProduct c where c.member.id = :memberId and c.product.id = :productId",
                    CartProduct.class)
                .setMaxResults(1)
                .setParameter("productId", productId)
                .setParameter("memberId", memberId)
                .getSingleResult();
        } catch (NoResultException e) {
            throw new CartException(e.getMessage());
        }
    }

    public void updateById(Long id, int quantity) {
        entityManager.createQuery(
                "update from CartProduct c set c.quantity = :quantity where c.id = :id")
            .setParameter("quantity", quantity)
            .setParameter("id", id)
            .executeUpdate();
    }

    public void deleteById(Long id) {
        entityManager.createQuery("delete from CartProduct c where c.id = :id")
            .setParameter("id", id)
            .executeUpdate();

    }

    public void deleteByMemberId(Long memberId) {
        entityManager.createQuery("delete from CartProduct c where c.member.id = :memberId")
            .setParameter("memberId", memberId)
            .executeUpdate();
    }
}
