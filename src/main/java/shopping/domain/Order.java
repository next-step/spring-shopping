package shopping.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import shopping.exception.OrderException;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    protected Order() {
    }

    public Order(Long id, Member member) {
        validateMember(member);
        this.id = id;
        this.member = member;
    }

    private void validateMember(Member member) {
        if (Objects.isNull(member)) {
            throw new OrderException("member 가 존재하지 않습니다");
        }
    }

    public Order(Member member) {
        this(null, member);
    }
}
