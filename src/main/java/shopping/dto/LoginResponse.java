package shopping.dto;

public class LoginResponse {

    private String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    private LoginResponse() {
    }

    public String getAccessToken() {
        return accessToken;
    }
}
