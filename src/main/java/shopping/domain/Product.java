package shopping.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "price", column = @Column(name = "price", nullable = false)))
    private Price price;

    public Product(String name, String imageUrl, Long price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = new Price(price);
    }

    public Product(Long id, String name, String imageUrl, Long price) {
        this(name, imageUrl, price);
        this.id = id;
    }

    protected Product() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getPrice() {
        return price.getPrice();
    }
}
