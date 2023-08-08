package shopping.order.app.domain;

import static shopping.order.app.domain.DomainFixture.Product.defaultProduct;

class DomainFixture {

    static class Order {

        static shopping.order.app.domain.Order fromCart(shopping.mart.app.domain.Cart cart) {
            return new shopping.order.app.domain.Order(cart);
        }
    }

    static class Cart {

        private static final long DEFAULT_CART_ID = 1L;
        private static final long DEFAULT_USER_ID = 1L;

        static shopping.mart.app.domain.Cart defaultCart() {
            shopping.mart.app.domain.Cart cart = new shopping.mart.app.domain.Cart(DEFAULT_CART_ID, DEFAULT_USER_ID);
            cart.addProduct(defaultProduct());
            cart.updateProduct(defaultProduct(), 100);
            return cart;
        }

    }

    static class Product {

        static shopping.mart.app.domain.Product defaultProduct() {
            return new shopping.mart.app.domain.Product("default", "images/default-image.png", "1000");
        }

    }


}
