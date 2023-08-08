package shopping.order.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import shopping.global.vo.Price;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderProduct> orderProducts;

    @Column(name = "total_price")
    @Embedded
    private Price totalPrice;

    @Column(name = "member_id")
    private Long memberId;

    protected Order() {
    }

    public Order(
        final List<OrderProduct> orderProducts,
        final Long memberId
    ) {
        this.orderProducts = orderProducts;
        this.totalPrice = new Price(calculateTotalPrice());
        this.memberId = memberId;
    }

    private int calculateTotalPrice() {
        return orderProducts.stream()
            .mapToInt(OrderProduct::calculatePrice)
            .sum();
    }

    public Long getId() {
        return id;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public int getTotalPrice() {
        return totalPrice.getPrice();
    }

    public Long getMemberId() {
        return memberId;
    }
}
