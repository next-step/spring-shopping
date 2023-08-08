package shopping.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import shopping.domain.Member;
import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.domain.Product;
import shopping.dto.request.OrderRequest;
import shopping.dto.response.OrderItemResponse;
import shopping.dto.response.OrderResponse;
import shopping.exception.MemberException;
import shopping.exception.OrderException;
import shopping.repository.MemberRepository;
import shopping.repository.OrderItemRepository;
import shopping.repository.OrderRepository;
import shopping.repository.ProductRepository;
import shopping.ui.argumentresolver.Login;

@Service
public class OrderService {

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    ProductRepository productRepository;
    MemberRepository memberRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
        ProductRepository productRepository, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public OrderResponse order(@Login long memberId, OrderRequest orderRequest) {
        // 사용자 정보 조회
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException("존재하지 않는 사용자 입니다"));

        // 주문 생성
        Order order = new Order(member);

        // 주문 상품 정보 생성
        List<OrderItemResponse> orderItems = orderRequest.getOrderItems()
            .stream()
            .map(orderItemRequest -> {
                Product product = productRepository.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new OrderException("존재하지 않는 상품 정보입니다"));
                OrderItem orderItem = orderItemRequest.toEntity(order, product);
                orderItemRepository.save(orderItem);
                return orderItem;

            })
            .map(OrderItemResponse::of)
            .collect(Collectors.toList());

        // 주문 정보 반환
        return new OrderResponse(order.getId(), orderItems);
    }
}
