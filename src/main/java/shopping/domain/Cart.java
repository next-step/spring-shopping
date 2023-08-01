package shopping.domain;

import shopping.exception.CartException;

public class Cart {

    public static final int MIN_PRODUCT_QUANTITY = 1;
    private Long id;

    private Member member;

    private Product product;

    private int quantity;

    public Cart(Member member, Product product, int quantity) {
        validateMember(member);
        validateProduct(product);
        validateQuantity(quantity);
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity < MIN_PRODUCT_QUANTITY) {
            throw new CartException("상품의 개수는 최소 1개여야합니다");
        }
    }

    private void validateProduct(Product product) {
        if (product == null) {
            throw new CartException("product가 존재하지 않습니다");
        }
    }

    private void validateMember(Member member) {
        if (member == null) {
            throw new CartException("member가 존재하지 않습니다");
        }
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
