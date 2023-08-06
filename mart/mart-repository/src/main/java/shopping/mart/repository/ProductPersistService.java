package shopping.mart.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import shopping.mart.domain.Product;
import shopping.mart.repository.entity.ProductEntity;
import shopping.mart.service.spi.ProductRepository;

@Repository
public class ProductPersistService implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    ProductPersistService(ProductJpaRepository productJpaRepository) {
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
        return toProduct(optionalProductEntity);
    }

    @Override
    public Optional<Product> findById(long id) {
        Optional<ProductEntity> optionalProductEntity = productJpaRepository.findById(id);
        return toProduct(optionalProductEntity);
    }

    private Optional<Product> toProduct(Optional<ProductEntity> optionalProductEntity) {
        return optionalProductEntity.map(
            productEntity -> new Product(productEntity.getId(), productEntity.getName(),
                productEntity.getImageUrl(),
                productEntity.getPrice()));
    }

    @Override
    public List<Product> findAllProducts() {
        List<ProductEntity> productEntities = productJpaRepository.findAll();
        return productEntities.stream()
            .map(productEntity -> new Product(productEntity.getId(), productEntity.getName(),
                productEntity.getImageUrl(), productEntity.getPrice()))
            .collect(Collectors.toList());
    }
}
