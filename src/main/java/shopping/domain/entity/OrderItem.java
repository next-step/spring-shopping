package shopping.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long productId;

    @Column(nullable = false, length = 20)
    private Name name;

    @Column(nullable = false)
    private Image image;

    @Column(nullable = false)
    private Price price;

    @Column(nullable = false)
    private Quantity quantity;

    protected OrderItem() {
    }

    public OrderItem(final Long id,
                     final Long productId,
                     final Name name,
                     final Image image,
                     final Price price,
                     final Quantity quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
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

    public Long getProductId() {
        return productId;
    }

    public Name getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }
}
