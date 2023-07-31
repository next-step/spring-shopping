package shopping.dto.response;

public class LoginResponse {
    private String accessToken;

    public LoginResponse() {
    }

    public LoginResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
