package shopping.order.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import shopping.infrastructure.dto.ExchangeRateResponse;
import shopping.order.domain.vo.ExchangeRate;
import shopping.order.domain.vo.Money;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Column(name = "total_price", nullable = false)
    @Embedded
    private Money totalPrice = new Money(0);

    @Column(name = "exchange_rate")
    @Embedded
    private ExchangeRate exchangeRate;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    protected Order() {
    }

    public Order(
        final List<OrderProduct> orderProducts,
        final Long memberId,
        final ExchangeRateResponse exchangeRate) {
        this.orderProducts = orderProducts;
        this.memberId = memberId;
        this.exchangeRate = new ExchangeRate(exchangeRate.getRate());
    }

    public Order(final Long memberId, final ExchangeRateResponse exchangeRate) {
        this.memberId = memberId;
        this.exchangeRate = new ExchangeRate(exchangeRate.getRate());
    }

    public void addOrderProducts(final List<OrderProduct> orderProducts) {
        orderProducts.forEach(this::addOrderProduct);
    }

    private void addOrderProduct(final OrderProduct orderProduct) {
        getOrderProducts().add(orderProduct);
        orderProduct.setOrder(this);

        this.totalPrice = totalPrice.addMoney(orderProduct.calculatePrice());
    }


    public boolean matchPersonOrder(final Long memberId) {
        return this.memberId == memberId;
    }

    public Long getId() {
        return id;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public long getTotalPrice() {
        return totalPrice.getPrice();
    }

    public Long getMemberId() {
        return memberId;
    }

    public Double getExchangeRate() {
        if (exchangeRate == null) {
            return null;
        }
        return exchangeRate.getExchangeRate();
    }
}
