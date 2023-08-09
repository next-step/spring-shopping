package shopping.domain.user;

import shopping.auth.PBKDF2PasswordEncoder;
import shopping.auth.PasswordEncoder;
import shopping.exception.general.InvalidRequestException;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class EncodedPassword {

    @Transient
    private final PasswordEncoder passwordEncoder;

    private String password;

    protected EncodedPassword() {
        this.passwordEncoder = new PBKDF2PasswordEncoder();
    }

    public EncodedPassword(String password) {
        this(password, new PBKDF2PasswordEncoder());
    }

    public EncodedPassword(String password, PasswordEncoder passwordEncoder) {
        validate(password);
        this.password = passwordEncoder.encode(password);
        this.passwordEncoder = passwordEncoder;
    }

    private void validate(String password) {
        if (password == null) {
            throw new InvalidRequestException("비밀번호는 null일 수 없습니다.");
        }
    }

    public boolean match(String password) {
        return passwordEncoder.match(password, this.password);
    }

    public String getPassword() {
        return password;
    }
}
