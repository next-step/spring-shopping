package shopping.domain.cart;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "quantity", column = @Column(name = "quantity", nullable = false)))
    private Quantity quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    protected OrderItem() {
    }

    public OrderItem(Long id, Product product, Quantity quantity, Order order) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

    public static OrderItem from(CartItem cartItem, Order order) {
        return new OrderItem(cartItem.getId(), cartItem.getProduct(), cartItem.getQuantity(), order);
    }

    public Money totalPrice() {
        return product.getPrice().multiply(quantity);
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }
}

