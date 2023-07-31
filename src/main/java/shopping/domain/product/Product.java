package shopping.domain.product;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Embedded
    private ProductImage image;

    @Embedded
    private ProductName name;

    @Embedded
    private Price price;

    protected Product() {

    }

    public Product(final String image, final String name, final int price) {
        this.image = ProductImage.from(image);
        this.name = ProductName.from(name);
        this.price = Price.from(price);
    }

    public Long getId() {
        return this.id;
    }

    public String getImage() {
        return this.image.getValue();
    }

    public String getName() {
        return this.name.getValue();
    }

    public int getPrice() {
        return this.price.getValue();
    }
}
