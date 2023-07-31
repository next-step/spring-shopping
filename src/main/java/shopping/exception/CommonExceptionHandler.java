package shopping.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(ShoppingException.class)
    public String exceptionHandleWithShoppingException(final ShoppingException e, final Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/" + e.getErrorPage();
    }

}
