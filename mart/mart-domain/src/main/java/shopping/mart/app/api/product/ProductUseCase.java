package shopping.mart.app.api.product;

import java.util.List;
import shopping.mart.app.api.product.response.ProductResponse;

public interface ProductUseCase {

    List<ProductResponse> findAllProducts();
}
