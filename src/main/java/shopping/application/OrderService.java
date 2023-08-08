package shopping.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Member;
import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.domain.Product;
import shopping.dto.request.OrderItemRequest;
import shopping.dto.request.OrderRequest;
import shopping.dto.response.OrderResponse;
import shopping.exception.MemberException;
import shopping.exception.OrderException;
import shopping.repository.MemberRepository;
import shopping.repository.OrderRepository;
import shopping.repository.ProductRepository;

@Service
@Transactional
public class OrderService {

    OrderRepository orderRepository;
    ProductRepository productRepository;
    MemberRepository memberRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
            MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public OrderResponse order(long memberId, OrderRequest orderRequest) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException("존재하지 않는 사용자 입니다"));

        Order order = new Order(member);
        List<OrderItem> orderItems = createOrderItems(orderRequest.getOrderItems(), order);

        orderRepository.save(order);

        return new OrderResponse(order.getId(), orderItems);
    }

    private List<OrderItem> createOrderItems(List<OrderItemRequest> orderItemRequests,
            Order order) {
        return orderItemRequests
            .stream()
            .map(orderItemRequest -> createOrderItem(orderItemRequest, order))
            .collect(Collectors.toList());
    }

    private OrderItem createOrderItem(OrderItemRequest orderItemRequest, Order order) {
        Product product = productRepository.findById(orderItemRequest.getProductId())
            .orElseThrow(() -> new OrderException("존재하지 않는 상품 정보입니다"));

        return orderItemRequest.toEntity(order, product);
    }
}
