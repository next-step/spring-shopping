package shopping.mart.dto;

import java.util.List;

public final class OrderDetailResponse {

    private List<OrderedProductResponse> items;
    private String totalPrice;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(List<OrderedProductResponse> items, String totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public List<OrderedProductResponse> getItems() {
        return items;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public static final class OrderedProductResponse {

        private String name;
        private String imageUrl;
        private String price;
        private int count;

        public OrderedProductResponse() {
        }

        public String getName() {
            return name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getPrice() {
            return price;
        }

        public int getCount() {
            return count;
        }
    }
}
