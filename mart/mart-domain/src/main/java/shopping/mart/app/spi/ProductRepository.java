package shopping.mart.app.spi;

import java.util.List;
import java.util.Optional;
import shopping.mart.app.domain.Product;

public interface ProductRepository {

    void saveProduct(Product product);

    Optional<Product> findByProductName(String name);

    Optional<Product> findById(long id);

    List<Product> findAllProducts();

}
