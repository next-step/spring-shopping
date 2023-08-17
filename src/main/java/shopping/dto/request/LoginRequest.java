package shopping.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import shopping.exception.EmailNotFoundException;
import shopping.exception.PasswordNotFoundException;

public class LoginRequest {

    private String email;
    private String password;

    private LoginRequest() {
    }

    @JsonCreator
    public LoginRequest(final String email, final String password) {
        validateEmailNotNull(email);
        validatePasswordNotNull(password);

        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
