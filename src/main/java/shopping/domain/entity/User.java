package shopping.domain.entity;

import shopping.domain.Email;
import shopping.domain.Password;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @AttributeOverride(name = "value", column = @Column(name = "email"))
    private Email email;

    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "password"))
    private Password password;

    protected User() {

    }

    public User(final Long id, final Email email, final Password password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}
