package shopping.auth.dto;

public class UserJoinRequest {

    private String email;
    private String password;

    UserJoinRequest() {
    }

    public UserJoinRequest(String email, String password) {
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
