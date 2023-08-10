package shopping.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import shopping.exception.OrderException;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Column(nullable = false)
    private Double exchangeRate;

    protected Order() {
    }

    public Order(Long id, Member member, Double exchangeRate) {
        validateMember(member);
        this.id = id;
        this.member = member;
        this.exchangeRate = exchangeRate;
    }

    public Order(Member member, Double exchangeRate) {
        this(null, member, exchangeRate);
    }

    private void validateMember(Member member) {
        if (Objects.isNull(member)) {
            throw new OrderException("member 가 존재하지 않습니다");
        }
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public long calculateTotalPrice() {
        return orderProducts.stream()
            .mapToLong(OrderProduct::calculatePrice)
            .sum();
    }
}
