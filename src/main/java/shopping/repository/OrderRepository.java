package shopping.repository;

import java.util.List;
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

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Optional<Order> findById(Long id) {
        return entityManager.createQuery(
                "select o from Order o join fetch o.orderItems where o.id = :id", Order.class)
            .setParameter("id", id)
            .getResultStream()
            .findFirst();
    }

    public List<Order> findByMemberId(Long memberId) {
        return entityManager.createQuery(
            "select o from Order o join fetch o.orderItems where o.member.id = :memberId", Order.class
        )
            .setParameter("memberId", memberId)
            .getResultList();
    }
}
