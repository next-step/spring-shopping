package shopping.domain.user;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import shopping.auth.PasswordEncoder;
import shopping.exception.auth.PasswordNotMatchException;

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

    public User(Long id, String email, String password, PasswordEncoder passwordEncoder) {
        this.id = id;
        this.email = new Email(email);
        this.encodedPassword = new EncodedPassword(password, passwordEncoder);
    }

    public User(String email, String password, PasswordEncoder passwordEncoder) {
        this(null, email, password, passwordEncoder);
    }

    public void matchPassword(String password, PasswordEncoder passwordEncoder) {
        if(!encodedPassword.match(password, passwordEncoder)) {
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
