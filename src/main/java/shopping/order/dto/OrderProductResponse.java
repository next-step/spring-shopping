package shopping.order.dto;

import shopping.order.domain.OrderProduct;

public class OrderProductResponse {

    private Long id;
    private String name;
    private int price;
    private String image;
    private int quantity;

    public OrderProductResponse(
        final Long id,
        final String name,
        final int price,
        final String image,
        final int quantity
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public static OrderProductResponse from(final OrderProduct orderProduct) {
        return new OrderProductResponse(
            orderProduct.getId(),
            orderProduct.getName(),
            orderProduct.getPrice(),
            orderProduct.getImage(),
            orderProduct.getQuantity()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public int getQuantity() {
        return quantity;
    }
}
