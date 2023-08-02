package shopping.service;

import java.util.List;
import org.springframework.stereotype.Service;
import shopping.domain.product.Product;
import shopping.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
