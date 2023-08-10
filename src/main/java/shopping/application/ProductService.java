package shopping.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Product;
import shopping.dto.response.ProductResponse;
import shopping.repository.ProductRepository;
import shopping.util.PageUtil;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> findAllByPage(Integer pageNumber, Integer pageSize) {
        int page = PageUtil.validatePageNumber(pageNumber);
        int size = PageUtil.validatePageSize(pageSize);

        Page<Product> products = productRepository
                .findAll(PageRequest.of(page, size));
        return products.map(ProductResponse::of);
    }
}
