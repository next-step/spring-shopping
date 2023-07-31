package shopping.product.service;

import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.tomcat.jni.Proc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.common.vo.Image;
import shopping.exception.WooWaException;
import shopping.product.domain.Product;
import shopping.product.dto.request.ProductCreationRequest;
import shopping.product.dto.request.ProductUpdateRequest;
import shopping.product.dto.response.ProductResponse;
import shopping.product.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductResponse readProduct(Long productId) {
        return ProductResponse.from(getProductById(productId));
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> readAllProduct() {
        return ProductResponse.listOf(productRepository.findAll());
    }

    public void createProduct(ProductCreationRequest productCreationRequest, Image productImage) {
        productRepository.save(productCreationRequest.toEntity(productImage));
    }

    public void updateProduct(ProductUpdateRequest productUpdateRequest) {
        Product product = getProductById(productUpdateRequest.getId());
        product.updateValue(productUpdateRequest.getName(), productUpdateRequest.getPrice());
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new WooWaException("존재하지 않은 Product입니다 id: \'" + productId + "\'", BAD_REQUEST));
    }
}
