package shopping.order.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import shopping.cart.domain.CartProduct;
import shopping.cart.domain.CartProductWithProduct;
import shopping.global.vo.Name;
import shopping.global.vo.Price;
import shopping.global.vo.Quantity;
import shopping.product.domain.Product;
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

    private OrderProduct(Long productId, String name, String image, int price, int quantity) {
        this.productId = productId;
        this.name = new Name(name);
        this.image = new ProductImage(image);
        this.price = new Price(price);
        this.quantity = new Quantity(quantity);
    }

    private static OrderProduct of(final CartProduct cartProduct, final Product product) {
        return new OrderProduct(
            cartProduct.getProductId(),
            product.getName(),
            product.getImage(),
            product.getPrice(),
            cartProduct.getQuantity()
        );
    }

    public static OrderProduct from(final CartProductWithProduct cartItem) {
        return OrderProduct.of(
            cartItem.getCartProduct(),
            cartItem.getProduct()
        );
    }


    public int calculatePrice() {
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

    public int getPrice() {
        return price.getPrice();
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}