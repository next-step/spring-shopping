package shopping.mart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import shopping.mart.app.domain.Product;
import shopping.mart.app.spi.ProductRepository;
import shopping.mart.repository.entity.ProductEntity;

@Repository
public class ProductPersistService implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductPersistService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public void saveProduct(final Product product) {
        ProductEntity productEntity = new ProductEntity(product.getId(), product.getName(),
            product.getImageUrl(),
            product.getPrice());
        productJpaRepository.save(productEntity);
    }

    @Override
    public Optional<Product> findByProductName(final String name) {
        Optional<ProductEntity> optionalProductEntity = productJpaRepository.findByName(name);
        return optionalProductEntity.map(ProductEntity::toDomain);
    }

    @Override
    public Optional<Product> findById(long id) {
        Optional<ProductEntity> optionalProductEntity = productJpaRepository.findById(id);
        return optionalProductEntity.map(ProductEntity::toDomain);
    }

    @Override
    public List<Product> findAllProducts() {
        List<ProductEntity> productEntities = productJpaRepository.findAll();
        return productEntities.stream()
            .map(ProductEntity::toDomain)
            .collect(Collectors.toList());
    }
}
