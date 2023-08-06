package shopping.mart.service.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import shopping.mart.service.ProductService;
import shopping.mart.service.dto.ProductCreateRequest;

@Component
final class ProductConfigurer {

    private static final ProductCreateRequest SOJU_PRODUCT_CREATE_REQUEST = new ProductCreateRequest("소주",
            "images/soju.jpeg",
            "5000");
    private static final ProductCreateRequest BEER_PRODUCT_CREATE_REQUEST = new ProductCreateRequest("맥주",
            "images/beer.jpeg",
            "5500");
    private static final ProductCreateRequest MAKGEOLLI_PRODUCT_CREATE_REQUEST = new ProductCreateRequest("막걸리",
            "images/makgeolli.png",
            "6000");

    private final ProductService productService;

    ProductConfigurer(final ProductService productService) {
        this.productService = productService;
    }

    @EventListener(ApplicationStartedEvent.class)
    void setDefaultProducts() {
        productService.saveProduct(SOJU_PRODUCT_CREATE_REQUEST);
        productService.saveProduct(BEER_PRODUCT_CREATE_REQUEST);
        productService.saveProduct(MAKGEOLLI_PRODUCT_CREATE_REQUEST);
    }

}
