package shopping.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected OrderItem() {
    }

    public OrderItem(final Long id,
                     final Long productId,
                     final Name name,
                     final Image image,
                     final Price price,
                     final Quantity quantity) {
        throw new UnsupportedOperationException();
    }

    public OrderItem(final Long productId,
                     final Name name,
                     final Image image,
                     final Price price,
                     final Quantity quantity) {
        this(null, productId, name, image, price, quantity);
    }

    public Long getId() {
        return id;
    }
}
