package shopping.domain.user;

import shopping.exception.auth.PasswordNotMatchException;

import javax.persistence.*;

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
    private EncodedPassword encodedPassword;

    protected User() {
    }

    public User(String email, String password) {
        this.email = new Email(email);
        this.encodedPassword = new EncodedPassword(password);
    }

    public User(Long id, String email, String password) {
        this(email, password);
        this.id = id;
    }

    public void matchPassword(String password) {
        if(!encodedPassword.match(password)) {
            throw new PasswordNotMatchException();
        }
    }

    public Long getId() {
        return id;
    }

    public EncodedPassword getPassword() {
        return encodedPassword;
    }

    public Email getEmail() {
        return email;
    }
}
