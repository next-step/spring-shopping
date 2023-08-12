package shopping.domain;

public interface TokenProvider<T> {

    String createToken(T id);

    T decodeToken(String token);
}
