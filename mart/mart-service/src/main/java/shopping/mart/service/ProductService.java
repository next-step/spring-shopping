package shopping.mart.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.domain.Product;
import shopping.mart.domain.exception.AlreadyExistProductException;
import shopping.mart.service.dto.ProductCreateRequest;
import shopping.mart.service.dto.ProductResponse;
import shopping.mart.service.spi.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void saveProduct(final ProductCreateRequest request) {
        throwIfExistProduct(request);
        Product product = new Product(request.getName(), request.getImageUrl(), request.getPrice());
        productRepository.saveProduct(product);
    }

    private void throwIfExistProduct(final ProductCreateRequest request) {
        productRepository.findByProductName(request.getName())
            .ifPresent(product -> {
                throw new AlreadyExistProductException(
                    MessageFormat.format("이미 존재하는 product\"{0}\" 입니다.", request));
            });
    }

    public List<ProductResponse> findAllProducts() {
        List<Product> products = productRepository.findAllProducts();
        return products.stream()
            .map(product -> new ProductResponse(product.getId(), product.getName(),
                product.getImageUrl(),
                product.getPrice()))
            .collect(Collectors.toList());
    }
}
