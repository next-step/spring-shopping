package shopping.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shopping.application.ProductService;

@Controller
public class PageController {

    private final ProductService productService;

    public PageController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String mainPage(final Model model) {
        model.addAttribute("products", productService.findAllProducts());

        return "index";
    }

    @GetMapping("/order-detail/{id}")
    public String orderDetailPage(final Model model, @PathVariable final Long id) {
        model.addAttribute("orderId", id);

        return "order-detail";
    }
}

