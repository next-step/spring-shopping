package shopping.domain;

public interface TokenProvider {

    String createToken(Long id);

    Long decodeToken(String token);
}
