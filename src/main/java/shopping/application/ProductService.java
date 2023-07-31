package shopping.application;

import org.springframework.stereotype.Service;
import shopping.domain.Product;
import shopping.dto.FindProductResponse;
import shopping.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<FindProductResponse> findAll() {

        return productRepository.findAll().stream()
                .map(FindProductResponse::of)
                .collect(Collectors.toList());
    }
}
