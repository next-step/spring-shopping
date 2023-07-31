package shopping.persist;

import java.util.Optional;
import shopping.domain.Product;

public class ProductRepository {
    public void saveProduct(final Product product) {
    }

    public Optional<Product> findByProductName(final String name) {
        return Optional.empty();
    }
}
