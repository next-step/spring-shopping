package shopping.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductEntity {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String imageUuid;
    private int price;

    public ProductEntity() {
    }

    public ProductEntity(final Long id, final String name, final String imageUuid,
        final int price) {
        this.id = id;
        this.name = name;
        this.imageUuid = imageUuid;
        this.price = price;
    }

    public ProductEntity(final String name, final String imageUuid, final int price) {
        this(null, name, imageUuid, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUuid() {
        return imageUuid;
    }

    public int getPrice() {
        return price;
    }
}
