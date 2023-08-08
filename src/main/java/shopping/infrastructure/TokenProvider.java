package shopping.infrastructure;

public interface TokenProvider {
    String create(final String payload);

    String getPayload(final String token);

    boolean validateToken(final String token);
}
