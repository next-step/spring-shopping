package shopping.persist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import shopping.domain.Product;
import shopping.persist.entity.ProductEntity;
import shopping.persist.repository.ProductJpaRepository;

@Repository
public class ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    ProductRepository(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public void saveProduct(final Product product) {
        ProductEntity productEntity = new ProductEntity(product.getId(), product.getName(), product.getImageUrl(),
                product.getPrice());
        productJpaRepository.save(productEntity);
    }

    public Optional<Product> findByProductName(final String name) {
        Optional<ProductEntity> optionalProductEntity = productJpaRepository.findByName(name);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            return Optional.of(new Product(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(),
                    productEntity.getPrice()));
        }
        return Optional.empty();
    }

    public List<Product> findAllProducts() {
        List<ProductEntity> productEntities = productJpaRepository.findAll();
        return productEntities.stream()
                .map(productEntity -> new Product(productEntity.getId(), productEntity.getName(),
                        productEntity.getImageUrl(), productEntity.getPrice()))
                .collect(Collectors.toList());
    }
}
