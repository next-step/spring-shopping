package shopping.order.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.app.api.cart.event.CartClearEvent;
import shopping.mart.app.domain.Cart;
import shopping.mart.app.spi.CartRepository;
import shopping.order.app.api.OrderUseCase;
import shopping.order.app.api.request.OrderRequest;
import shopping.order.app.domain.Order;
import shopping.order.app.domain.Receipt;
import shopping.order.app.spi.ReceiptRepository;

@Service
@Transactional(readOnly = true)
public class OrderService implements OrderUseCase {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final CartRepository cartRepository;
    private final ReceiptRepository receiptRepository;

    public OrderService(ApplicationEventPublisher applicationEventPublisher, CartRepository cartRepository,
            ReceiptRepository receiptRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.cartRepository = cartRepository;
        this.receiptRepository = receiptRepository;
    }

    @Override
    @Transactional
    public void order(OrderRequest orderRequest) {
        Cart cart = cartRepository.getById(orderRequest.getCartId());

        Order order = new Order(cart);
        Receipt receipt = order.purchase();

        receiptRepository.persist(receipt);

        applicationEventPublisher.publishEvent(new CartClearEvent(orderRequest.getCartId()));
    }
}
