package shopping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shopping.domain.member.Member;
import shopping.domain.order.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByMember(final Member member);
}
