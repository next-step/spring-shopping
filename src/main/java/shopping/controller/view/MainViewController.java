package shopping.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.service.ProductService;

@Controller
public class MainViewController {

    private final ProductService productService;

    public MainViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getMainPage(final Model model) {
        model.addAttribute("products", productService.findAllProducts());

        return "index";
    }
}
