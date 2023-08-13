package shopping.domain.user;

import javax.persistence.Embeddable;
import shopping.auth.PasswordEncoder;
import shopping.exception.general.InvalidRequestException;

@Embeddable
public class EncodedPassword {

    private String password;

    protected EncodedPassword() {

    }

    public EncodedPassword(String password, PasswordEncoder passwordEncoder) {
        validate(password);
        this.password = passwordEncoder.encode(password);
    }

    private void validate(String password) {
        if (password == null) {
            throw new InvalidRequestException("비밀번호는 null일 수 없습니다.");
        }
    }

    public boolean match(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.match(password, this.password);
    }

    public String getPassword() {
        return password;
    }
}
