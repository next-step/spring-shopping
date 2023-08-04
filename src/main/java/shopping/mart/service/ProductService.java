package shopping.mart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.domain.Product;
import shopping.mart.dto.ProductCreateRequest;
import shopping.mart.dto.ProductResponse;
import shopping.mart.persist.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void saveProduct(final ProductCreateRequest request) {
        Product product = new Product(request.getName(), request.getImageUrl(), request.getPrice());

        productRepository.saveProduct(product);
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
