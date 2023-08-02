package shopping.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {
    }

    @JsonCreator
    public LoginRequest(String email, String password) {
        validateEmailNotNull(email);
        validatePasswordNotNull(password);

        this.email = email;
        this.password = password;
    }

    private void validatePasswordNotNull(String password) {
        if (password == null) {
            throw new ShoppingException(ErrorType.PASSWORD_NULL);
        }
    }

    private void validateEmailNotNull(String email) {
        if (email == null) {
            throw new ShoppingException(ErrorType.EMAIL_NULL);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
