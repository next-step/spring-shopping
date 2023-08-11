package shopping.mart.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.core.util.ErrorTemplate;
import shopping.mart.domain.usecase.product.ProductUseCase;
import shopping.mart.domain.usecase.product.response.ProductResponse;
import shopping.mart.domain.exception.AlreadyExistProductException;
import shopping.mart.domain.exception.InvalidPriceException;
import shopping.mart.domain.exception.InvalidProductNameException;

@Controller
public class ProductViewController {

    private final ProductUseCase productUseCase;

    public ProductViewController(final ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping("/")
    public String findAllProducts(Model model) {
        List<ProductResponse> response = productUseCase.findAllProducts();
        model.addAttribute("products", response);
        return "index";
    }

    @ExceptionHandler({InvalidPriceException.class,
            InvalidProductNameException.class,
            AlreadyExistProductException.class})
    private ErrorTemplate handleBadRequest(RuntimeException runtimeException) {
        return new ErrorTemplate(runtimeException.getMessage());
    }
}
