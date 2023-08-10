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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

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

    @Column(name = "product_id")
    private Long productId;

    protected OrderProduct() {
    }
}
