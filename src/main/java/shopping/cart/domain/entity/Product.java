package shopping.cart.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String imageUuidFileName;
    private int price;

    public Product() {
    }

    public Product(final Long id, final String name, final String imageUuidFileName,
        final int price) {
        this.id = id;
        this.name = name;
        this.imageUuidFileName = imageUuidFileName;
        this.price = price;
    }

    public Product(final String name, final String imageUuidFileName, final int price) {
        this(null, name, imageUuidFileName, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUuidFileName() {
        return imageUuidFileName;
    }

    public int getPrice() {
        return price;
    }
}
