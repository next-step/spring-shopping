package shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorPageExceptionHandler {

    @ExceptionHandler(ShoppingException.class)
    public String exceptionHandleWithShoppingException(final ShoppingException e, final Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return renderErrorPage(e.getHttpStatus());
    }

    private String renderErrorPage(final HttpStatus httpStatus) {
        return "error/" + convertHttpStatusToPage(httpStatus);
    }

    private String convertHttpStatusToPage(final HttpStatus httpStatus) {
        return (httpStatus.value() / 100) + "xx";
    }
}
