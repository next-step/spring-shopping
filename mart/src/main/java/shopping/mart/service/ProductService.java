package shopping.mart.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.domain.Product;
import shopping.mart.domain.exception.AlreadyExistProductException;
import shopping.mart.dto.ProductCreateRequest;
import shopping.mart.dto.ProductResponse;
import shopping.mart.persist.ProductPersistService;

@Service
public class ProductService {

    private final ProductPersistService productPersistService;

    public ProductService(ProductPersistService productPersistService) {
        this.productPersistService = productPersistService;
    }

    @Transactional
    public void saveProduct(final ProductCreateRequest request) {
        throwIfExistProduct(request);
        Product product = new Product(request.getName(), request.getImageUrl(), request.getPrice());
        productPersistService.saveProduct(product);
    }

    private void throwIfExistProduct(final ProductCreateRequest request) {
        productPersistService.findByProductName(request.getName())
            .ifPresent(product -> {
                throw new AlreadyExistProductException(
                    MessageFormat.format("이미 존재하는 product\"{0}\" 입니다.", request));
            });
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAllProducts() {
        List<Product> products = productPersistService.findAllProducts();
        return products.stream()
            .map(product -> new ProductResponse(product.getId(), product.getName(),
                product.getImageUrl(),
                product.getPrice()))
            .collect(Collectors.toList());
    }
}
