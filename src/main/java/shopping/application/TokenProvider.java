package shopping.application;

public interface TokenProvider {
    String create(final String payload);
}
