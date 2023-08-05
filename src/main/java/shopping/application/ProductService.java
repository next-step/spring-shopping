package shopping.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Product;
import shopping.dto.response.ProductResponse;
import shopping.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private static final int PAGE_START_NUMBER = 1;
    private static final int MIN_PAGE_SIZE = 6;
    private static final int MAX_PAGE_SIZE = 60;

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> findAllByPage(Integer pageNumber, Integer pageSize) {
        int page = validatePageNumber(pageNumber);
        int size = validatePageSize(pageSize);

        Page<Product> products = productRepository
                .findAll(PageRequest.of(page - PAGE_START_NUMBER, size));
        return products.map(ProductResponse::of);
    }

    private int validatePageNumber(Integer pageNumber) {
        return pageNumber < PAGE_START_NUMBER
                ? PAGE_START_NUMBER : pageNumber;
    }

    private int validatePageSize(Integer pageSize) {
        if (pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        }
        if (pageSize < MIN_PAGE_SIZE) {
            return MIN_PAGE_SIZE;
        }
        return pageSize;
    }
}
