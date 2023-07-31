package shopping.domain;

public class User {

    private final Long id;
    private final String email;
    private final String password;

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
