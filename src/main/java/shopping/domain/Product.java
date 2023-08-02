package shopping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private Name name;

    @Column(nullable = false)
    private Image image;

    @Column
    private Price price;

    protected Product() {
    }

    public Product(Long id, String name, String image, int price) {
        this.id = id;
        this.name = new Name(name);
        this.image = new Image(image);
        this.price = new Price(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public String getImage() {
        return image.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }
}
