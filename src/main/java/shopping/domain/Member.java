package shopping.domain;

import org.springframework.util.StringUtils;
import shopping.exception.MemberException;
import shopping.utils.EmailUtils;

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

    public Member(Long id, String email, String password) {
        validateEmail(email);
        validatePassword(password);
        this.id = id;
        this.email = email;
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

    private void validateEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new MemberException("사용자 이메일이 존재하지 않습니다");
        }
        if (!EmailUtils.isValidEmail(email)) {
            throw new MemberException("올바른 이메일 형식이 아닙니다");
        }
    }

    protected Member() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
