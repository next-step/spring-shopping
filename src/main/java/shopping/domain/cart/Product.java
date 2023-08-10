package shopping.domain.cart;

import javax.persistence.*;

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
    @AttributeOverride(name = "price", column = @Column(name = "price", nullable = false))
    @AttributeOverride(name = "type", column = @Column(name = "type", nullable = false))
    private Money price;

    public Product(String name, String imageUrl, Double price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = new Money(price);
    }

    public Product(Long id, String name, String imageUrl, Double price) {
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

    public Money getPrice() {
        return price;
    }
}
