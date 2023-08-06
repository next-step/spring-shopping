package shopping.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String imageFileName;
    private int price;

    public ProductEntity() {
    }

    public ProductEntity(final Long id, final String name, final String imageFileName,
        final int price) {
        this.id = id;
        this.name = name;
        this.imageFileName = imageFileName;
        this.price = price;
    }

    public ProductEntity(final String name, final String imageFileName, final int price) {
        this(null, name, imageFileName, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public int getPrice() {
        return price;
    }
}
