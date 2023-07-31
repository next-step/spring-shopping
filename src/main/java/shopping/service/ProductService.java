package shopping.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Product;
import shopping.domain.exception.StatusCodeException;
import shopping.dto.ProductCreateRequest;
import shopping.dto.ProductResponse;
import shopping.persist.ProductRepository;

@Service
public class ProductService {

    private static final String ALREADY_EXIST_PRODUCT = "PRODUCT-SERVICE-401";

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
                    throw new StatusCodeException(MessageFormat.format("이미 존재하는 product\"{0}\" 입니다.", request),
                            ALREADY_EXIST_PRODUCT);
                });
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAllProducts() {
        List<Product> products = productRepository.findAllProducts();
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getImageUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }
}
