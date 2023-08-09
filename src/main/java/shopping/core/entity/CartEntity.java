package shopping.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class CartEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    protected CartEntity() {
    }

    public CartEntity(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "CartEntity{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }
}
