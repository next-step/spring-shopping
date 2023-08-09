package shopping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.util.StringUtils;
import shopping.exception.MemberException;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Email email;

    @Column(nullable = false)
    private String password;

    public Member(Long id, String email, String password) {
        validatePassword(password);
        this.id = id;
        this.email = new Email(email);
        this.password = password;
    }

    public Member(String email, String password) {
        this(null, email, password);
    }

    private void validatePassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new MemberException("사용자 비밀번호가 존재하지 않습니다");
        }
    }

    public boolean matchId(Long other) {
        return this.id.equals(other);
    }

    protected Member() {
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
