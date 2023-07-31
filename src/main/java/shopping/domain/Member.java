package shopping.domain;

import org.springframework.util.StringUtils;
import shopping.exception.MemberException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.regex.Pattern;

@Entity
public class Member {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

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
            throw new MemberException("사용자 이름이 존재하지 않습니다");
        }
    }

    private void validateEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new MemberException("사용자 이메일이 존재하지 않습니다");
        }
        if (!pattern.matcher(email).matches()) {
            throw new MemberException("이메일 형식이 올바르지 않습니다");
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

    public String getPassword() {
        return password;
    }
}
