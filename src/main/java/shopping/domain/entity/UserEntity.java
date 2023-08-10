package shopping.domain.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;

    protected UserEntity() {
    }

    public UserEntity(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserEntity(final String email, final String password) {
        this(null, email, password);
    }

    public void matchPassword(final String password) {
        if (!Objects.equals(this.password, password)) {
            throw new ShoppingException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
