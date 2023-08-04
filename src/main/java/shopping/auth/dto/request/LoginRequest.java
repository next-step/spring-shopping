package shopping.auth.dto.request;

public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
