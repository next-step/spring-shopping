package shopping.domain.cart;

import javax.persistence.*;

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

