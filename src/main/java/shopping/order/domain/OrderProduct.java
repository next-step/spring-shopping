package shopping.order.domain;

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
import shopping.cart.domain.CartProduct;
import shopping.cart.dto.CartProductWithProduct;
import shopping.global.vo.Name;
import shopping.global.vo.Price;
import shopping.global.vo.Quantity;
import shopping.product.domain.Product;
import shopping.product.domain.ProductImage;

@Entity
@Table(name = "ORDER_PRODUCT")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "name", nullable = false)
    @Embedded
    private Name name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "image")
    @Embedded
    private ProductImage image;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    @Embedded
    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity;

    protected OrderProduct() {
    }

    public OrderProduct(
        final Long productId,
        final Name name,
        final ProductImage image,
        final Price price,
        final Quantity quantity
    ) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderProduct from(final CartProductWithProduct cartItem) {
        return new OrderProduct(
            cartItem.getCartProduct(),
            cartItem.getProduct()
        );
    }

    private OrderProduct(final CartProduct cartProduct, final Product product) {
        this(
            cartProduct.getProductId(),
            new Name(product.getName()),
            new ProductImage(product.getImage()),
            new Price(product.getPrice()),
            new Quantity(cartProduct.getQuantity())
        );
    }

    void setOrder(final Order order) {
        this.order = order;
    }

    public long calculatePrice() {
        return getPrice() * getQuantity();
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

    public long getPrice() {
        return price.getPrice();
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}