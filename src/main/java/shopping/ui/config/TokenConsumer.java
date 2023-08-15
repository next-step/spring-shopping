package shopping.ui.config;

public interface TokenConsumer {
    String getPayload(final String token);

    boolean validateToken(final String token);
}
