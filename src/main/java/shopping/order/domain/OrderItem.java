package shopping.order.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import shopping.cart.domain.vo.Quantity;
import shopping.common.vo.Image;
import shopping.common.vo.Money;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String productName;
    @Embedded
    private Money productPrice;
    @Embedded
    private Image productImage;
    @Embedded
    private Quantity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    protected OrderItem() {
    }

    public OrderItem(
        Long productId,
        String productName,
        Money productPrice,
        Image productImage,
        Quantity quantity
    ) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.quantity = quantity;
    }

    public OrderItem(
        Long productId,
        String productName,
        String productPrice,
        String productImage,
        int quantity
    ) {
        this(productId, productName, new Money(productPrice), new Image(productImage),
            new Quantity(quantity));
    }

    void updateOrder(Order order) {
        this.order = order;
    }

    public Money getTotalPrice() {
        return productPrice.multiply(quantity.getValue());
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Money getProductPrice() {
        return productPrice;
    }

    public Image getProductImage() {
        return productImage;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }
}
