package shopping.domain;

public class Cart {

    private Long id;

    private Member member;

    private Product product;

    private int quantity;

    public Cart(Member member, Product product, int quantity) {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }
}
