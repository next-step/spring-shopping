package shopping.domain.user;

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
    private Password password;

    protected User() {
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

    public boolean isSame(User other) {
        return this.id.equals(other.id);
    }
}
