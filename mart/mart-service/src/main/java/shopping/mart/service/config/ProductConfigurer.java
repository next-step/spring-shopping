package shopping.mart.service.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import shopping.mart.domain.Product;
import shopping.mart.service.spi.ProductRepository;

@Component
final class ProductConfigurer {

    private static final Product SOJU_PRODUCT = new Product("소주", "images/soju.jpeg",
        "5000");
    private static final Product BEER_PRODUCT = new Product("맥주", "images/beer.jpeg",
        "5500");
    private static final Product MAKGEOLLI_PRODUCT = new Product("막걸리", "images/makgeolli.png",
        "6000");

    private final ProductRepository productRepository;

    public ProductConfigurer(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationStartedEvent.class)
    private void setDefaultProducts() {
        productRepository.saveProduct(SOJU_PRODUCT);
        productRepository.saveProduct(BEER_PRODUCT);
        productRepository.saveProduct(MAKGEOLLI_PRODUCT);
    }

}
