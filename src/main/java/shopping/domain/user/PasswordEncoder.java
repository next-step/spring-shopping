package shopping.domain.user;

public interface PasswordEncoder {
    String encode(final String password);
}
