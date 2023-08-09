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

    public long save(Order order) {
        entityManager.persist(order);
        return order.getId();
    }

    public Optional<Order> findById(long orderId) {
        return Optional.ofNullable(entityManager.find(Order.class, orderId));
    }

    public List<Order> findByMemberId(long memberId) {
        return entityManager
            .createQuery("select o from Order o where o.member.id = :memberId", Order.class)
            .setParameter("memberId", memberId)
            .getResultList();
    }
}
