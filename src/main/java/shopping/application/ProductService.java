package shopping.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Product;
import shopping.dto.ProductRequest;
import shopping.dto.ProductResponse;
import shopping.repository.ProductRepository;

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

    @Transactional
    public void insert(ProductRequest productRequest) {
        productRepository.save(new Product(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice()
        ));
    }
}
