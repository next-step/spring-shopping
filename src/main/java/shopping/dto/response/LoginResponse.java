package shopping.dto.response;

public class LoginResponse {

    private String token;

    private LoginResponse() {
    }

    public LoginResponse(final String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
