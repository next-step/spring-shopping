package shopping.mart.persist;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import shopping.mart.domain.Product;
import shopping.mart.persist.entity.ProductEntity;
import shopping.mart.persist.repository.ProductJpaRepository;

@Repository
public class ProductPersistService {

    private final ProductJpaRepository productJpaRepository;

    ProductPersistService(ProductJpaRepository productJpaRepository) {
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

    public Optional<Product> findById(long id) {
        Optional<ProductEntity> optionalProductEntity = productJpaRepository.findById(id);
        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = optionalProductEntity.get();
            return Optional.of(new Product(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(),
                    productEntity.getPrice()));
        }
        return Optional.empty();
    }
}
