package shopping.mart.domain.usecase.product;

import java.util.List;
import shopping.mart.domain.usecase.product.response.ProductResponse;

public interface ProductUseCase {

    List<ProductResponse> findAllProducts();
}
