package shopping.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    @Embedded
    private ProductName name;

    @Column(name = "image")
    @Embedded
    private ProductImage image;

    @Column(name = "price")
    @Embedded
    private ProductPrice price;

    protected Product() {
    }

    public Product(
        final String name,
        final String image,
        final int price
    ) {
        this.name = new ProductName(name);
        this.image = new ProductImage(image);
        this.price = new ProductPrice(price);
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

    public int getPrice() {
        return this.price.getPrice();
    }
}
