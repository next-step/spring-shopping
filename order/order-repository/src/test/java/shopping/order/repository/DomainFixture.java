package shopping.order.repository;

class DomainFixture {

    static class Cart {

        private static final long DEFAULT_CART_ID = 1L;
        private static final long DEFAULT_USER_ID = 1L;

        static shopping.mart.domain.Cart defaultCart() {
            shopping.mart.domain.Cart cart = new shopping.mart.domain.Cart(DEFAULT_CART_ID, DEFAULT_USER_ID);
            cart.addProduct(Product.defaultProduct());
            cart.updateProduct(Product.defaultProduct(), 100);
            return cart;
        }
    }

    static class Product {

        static shopping.mart.domain.Product defaultProduct() {
            return new shopping.mart.domain.Product(0L, "default", "images/default-image.png", "1000");
        }
    }
}
