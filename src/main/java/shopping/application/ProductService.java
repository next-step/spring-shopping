package shopping.application;

import org.springframework.stereotype.Service;
import shopping.dto.response.ProductResponse;
import shopping.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toUnmodifiableList());
    }
}
