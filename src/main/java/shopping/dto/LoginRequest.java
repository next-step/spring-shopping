package shopping.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import shopping.domain.vo.Email;
import shopping.domain.vo.Password;
import shopping.exception.EmailNotFoundException;
import shopping.exception.PasswordNotFoundException;
import shopping.infrastructure.SHA256PasswordEncoder;

public class LoginRequest {

    private Email email;
    private Password password;

    private LoginRequest() {
    }

    @JsonCreator
    public LoginRequest(final String email, final String password) {
        validateEmailNotNull(email);
        validatePasswordNotNull(password);

        this.email = new Email(email);
        this.password = Password.createEncodedPassword(password, new SHA256PasswordEncoder());
    }

    private void validatePasswordNotNull(final String password) {
        if (password == null) {
            throw new PasswordNotFoundException();
        }
    }

    private void validateEmailNotNull(final String email) {
        if (email == null) {
            throw new EmailNotFoundException();
        }
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}
