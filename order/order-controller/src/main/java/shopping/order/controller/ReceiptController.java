package shopping.order.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.domain.usecase.Token;
import shopping.core.util.ErrorTemplate;
import shopping.order.domain.usecase.receipt.ReceiptUseCase;
import shopping.order.domain.usecase.receipt.response.ReceiptDetailResponse;
import shopping.order.domain.usecase.receipt.response.ReceiptResponse;
import shopping.order.domain.exception.DoesNotFindReceiptException;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptUseCase receiptUseCase;
    private final Token token;

    public ReceiptController(ReceiptUseCase receiptUseCase, Token token) {
        this.receiptUseCase = receiptUseCase;
        this.token = token;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReceiptResponse> receiptHistory() {
        return receiptUseCase.findAllByUserId(Long.parseLong(token.decrypted()));
    }

    @GetMapping("/{receiptId}")
    public ReceiptDetailResponse receiptDetail(@PathVariable("receiptId") long receiptId) {
        return receiptUseCase.getByIdAndUserId(receiptId, Long.parseLong(token.decrypted()));
    }

    @ExceptionHandler(DoesNotFindReceiptException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorTemplate handleDoesNotFindReceiptException(DoesNotFindReceiptException doesNotFindReceiptException) {
        return new ErrorTemplate(doesNotFindReceiptException.getMessage());
    }

}
