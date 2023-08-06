package shopping.mart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.domain.Product;
import shopping.mart.service.dto.ProductResponse;
import shopping.mart.service.spi.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
