package shopping.order.service;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.app.api.cart.CartUseCase;
import shopping.mart.app.api.cart.response.CartResponse;
import shopping.mart.app.api.cart.response.CartResponse.ProductResponse;
import shopping.order.app.api.order.OrderUseCase;
import shopping.order.app.api.order.request.OrderRequest;
import shopping.order.app.domain.Order;
import shopping.order.app.domain.Product;
import shopping.order.app.domain.Receipt;
import shopping.order.app.spi.ExchangeVendor;
import shopping.order.app.spi.ReceiptRepository;

@Service
@Transactional(readOnly = true)
public class OrderService implements OrderUseCase {

    private final CartUseCase cartUseCase;
    private final ReceiptRepository receiptRepository;
    private final ExchangeVendor exchangeVendor;

    public OrderService(CartUseCase cartUseCase, ReceiptRepository receiptRepository, ExchangeVendor exchangeVendor) {
        this.cartUseCase = cartUseCase;
        this.receiptRepository = receiptRepository;
        this.exchangeVendor = exchangeVendor;
    }

    @Override
    @Transactional
    public long order(OrderRequest orderRequest) {
        CartResponse cartResponse = cartUseCase.getCart(orderRequest.getUserId());

        Order order = new Order(orderRequest.getUserId(), toProducts(cartResponse));
        Receipt receipt = order.purchase(exchangeVendor.currentExchange());

        long receiptId = receiptRepository.persist(receipt);

        cartUseCase.clearCart(orderRequest.getUserId());

        return receiptId;
    }

    private Map<Product, Integer> toProducts(CartResponse cartResponse) {
        return cartResponse.getProductResponses()
                .stream()
                .collect(Collectors.toMap(this::toProduct, ProductResponse::getCount));
    }

    private Product toProduct(ProductResponse productResponse) {
        return new Product(productResponse.getId(), productResponse.getName(),
                new BigInteger(productResponse.getPrice()), productResponse.getImageUrl());
    }

}
