package shopping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders_item")
public class OrderItem {

    private static final int MAX_NAME_LENGTH = 25;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Long productId;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String imageUrl;

    protected OrderItem() {
    }

    public OrderItem(Long productId, String name, long price, int quantity,
            String imageUrl) {
        this(null, productId, name, price, quantity, imageUrl);
    }

    public OrderItem(Long id, Long productId, String name, long price, int quantity,
        String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public long calculateTotalPrice() {
        return price * quantity;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
