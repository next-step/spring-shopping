package shopping.domain;

import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public Member(String email, String password) {
        validateEmail(email);
        validatePassword(password);
        this.email = email;
        this.password = password;
    }

    private void validatePassword(String password) {
        if (StringUtils.isEmpty(password)) {

        }
    }

    private void validateEmail(String email) {

    }

    protected Member() {
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
