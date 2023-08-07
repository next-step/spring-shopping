package shopping.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import shopping.domain.Email;
import shopping.domain.Password;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;
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
            throw new ShoppingException(ErrorType.PASSWORD_NULL);
        }
    }

    private void validateEmailNotNull(final String email) {
        if (email == null) {
            throw new ShoppingException(ErrorType.EMAIL_NULL);
        }
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
