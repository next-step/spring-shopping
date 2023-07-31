package shopping.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.dto.response.ProductResponse;
import shopping.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll().stream()
            .map(ProductResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }
}
