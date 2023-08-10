package shopping.product.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import shopping.global.vo.Name;
import shopping.global.vo.Price;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    @Embedded
    private Name name;

    @Column(name = "image")
    @Embedded
    private ProductImage image;

    @Column(name = "price")
    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(
        final String name,
        final String image,
        final int price
    ) {
        this.name = new Name(name);
        this.image = new ProductImage(image);
        this.price = new Price(price);
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name.getName();
    }

    public String getImage() {
        return this.image.getImage();
    }

    public long getPrice() {
        return this.price.getPrice();
    }
}
