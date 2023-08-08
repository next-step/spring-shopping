package shopping.order.domain;

import javax.persistence.*;
import shopping.global.vo.Name;
import shopping.global.vo.Price;
import shopping.global.vo.Quantity;
import shopping.product.domain.ProductImage;

@Entity
@Table(name = "order_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    @Embedded
    private Name name;

    @Column(name = "image")
    @Embedded
    private ProductImage image;

    @Column(name = "price")
    @Embedded
    private Price price;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity = new Quantity(1);

    protected OrderProduct() {
    }

    public OrderProduct(Long productId, Name name, ProductImage image, Price price,
        Quantity quantity) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name.getName();
    }

    public String getImage() {
        return image.getImage();
    }

    public int getPrice() {
        return price.getPrice();
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}