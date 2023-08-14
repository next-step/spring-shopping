package shopping.dto.response;

import shopping.domain.entity.OrderItemEntity;

public class OrderItemResponse {

    private final Long id;
    private final String name;
    private final String imageFileName;
    private final int totalPrice;
    private final Double totalPriceUSD;
    private final int totalQuantity;

    public OrderItemResponse(Long id, String name, String imageFileName, int totalPrice,
        Double totalPriceUSD, int totalQuantity) {
        this.id = id;
        this.name = name;
        this.imageFileName = imageFileName;
        this.totalPrice = totalPrice;
        this.totalPriceUSD = totalPriceUSD;
        this.totalQuantity = totalQuantity;
    }

    public static OrderItemResponse from(OrderItemEntity orderItem) {
        return new OrderItemResponse(
            orderItem.getId(),
            orderItem.getName(),
            orderItem.getImageFileName(),
            orderItem.getTotalPrice(),
            orderItem.getTotalPriceUSD(),
            orderItem.getTotalQuantity()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Double getTotalPriceUSD() {
        return totalPriceUSD;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

}
