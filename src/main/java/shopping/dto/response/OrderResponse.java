package shopping.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import shopping.domain.Order;

public class OrderResponse {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy년 MM월 dd일 HH시 mm분 ss초").withZone(ZoneId.of("Asia/Seoul"));
    private final Long id;
    private final Long totalPrice;
    private final Double ratio;
    private final String createdDate;
    private final List<OrderItemResponse> orderItems;

    private OrderResponse(Long id, Long totalPrice, Double ratio, Instant createdDate,
            List<OrderItemResponse> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.ratio = ratio;
        this.createdDate = DATE_TIME_FORMATTER.format(createdDate);
        this.orderItems = orderItems;
    }

    @JsonCreator
    protected OrderResponse(Long id, Long totalPrice, Double ratio, String createdDate,
            List<OrderItemResponse> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.ratio = ratio;
        this.createdDate = createdDate;
        this.orderItems = orderItems;
    }

    public static OrderResponse of(Order order) {
        return new OrderResponse(order.getId(),
                order.getTotalPrice().getPrice(),
                order.getRatio(),
                order.getCreatedDate(),
                order.getOrderItems().stream()
                        .map(OrderItemResponse::of)
                        .collect(Collectors.toUnmodifiableList()));
    }

    public Long getId() {
        return id;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public Double getRatio() {
        return ratio;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
}
