package shopping.mart.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import shopping.mart.app.domain.Product;

@Entity
@Table(name = "product")
public class ProductEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "price", nullable = false)
    private String price;

    public ProductEntity() {
    }

    public ProductEntity(final Long id, final String name, final String imageUrl,
        final String price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product toDomain() {
        return new Product(id, name, imageUrl, price);
    }

    public Long getId() {
        return id;
    }
}
