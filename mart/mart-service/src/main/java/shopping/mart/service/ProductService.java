package shopping.mart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.domain.usecase.product.ProductUseCase;
import shopping.mart.domain.usecase.product.response.ProductResponse;
import shopping.mart.domain.Product;
import shopping.mart.domain.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> findAllProducts() {
        List<Product> products = productRepository.findAllProducts();
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(),
                        product.getImageUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());
    }
}
