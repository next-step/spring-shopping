package shopping.domain;

import java.util.Objects;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "email", column = @Column(name = "email", nullable = false, length = 100)))
    private Email email;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "password", column = @Column(name = "password", nullable = false, length = 100)))
    private Password password;

    public User() {
    }

    public User(String email, String password) {
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public User(Long id, String email, String password) {
        this(email, password);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getEmail() {
        return email.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(password,
                user.password) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, email);
    }
}
