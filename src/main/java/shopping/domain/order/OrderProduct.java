package shopping.domain.order;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import shopping.domain.product.ProductImage;
import shopping.domain.product.ProductName;
import shopping.domain.product.ProductPrice;

@Entity
@Table(name = "order_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // TODO: 양방향 필요?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // TODO: Product 정보 VO로 합치기
    @Embedded
    @AttributeOverride(name = "image", column = @Column(name = "ordered_image"))
    private ProductImage orderedImage;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "ordered_name"))
    private ProductName orderedName;


    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "ordered_price"))
    private ProductPrice orderedPrice;

    @Embedded
    @AttributeOverride(name = "quantity", column = @Column(name = "quantity"))
    private OrderProductQuantity quantity;

    protected OrderProduct() {
    }

    public OrderProduct(
        final String orderedImage,
        final String orderedName,
        final int orderedPrice,
        final int quantity
    ) {
        this.orderedImage = new ProductImage(orderedImage);
        this.orderedName = new ProductName(orderedName);
        this.orderedPrice = new ProductPrice(orderedPrice);
        this.quantity = new OrderProductQuantity(quantity);
    }

    public String getOrderedImage() {
        return this.orderedImage.getImage();
    }

    public String getOrderedName() {
        return this.orderedName.getName();
    }

    public int getOrderedPrice() {
        return this.orderedPrice.getPrice();
    }

    public int getQuantity() {
        return this.quantity.getQuantity();
    }
}
