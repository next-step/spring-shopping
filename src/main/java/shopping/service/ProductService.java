package shopping.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.dto.response.ProductResponse;
import shopping.dto.response.ProductResponses;
import shopping.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductResponses readProducts() {
        final List<ProductResponse> productResponses = productRepository.findAll().stream()
                .map(ProductResponse::from)
                .collect(toList());

        return ProductResponses.from(productResponses);
    }
}
