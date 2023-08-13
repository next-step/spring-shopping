package shopping.dto.request;

import shopping.exchange.CurrencyType;

public class OrderExchangeRequest {

    private final Long memberId;
    private final Long orderId;
    private CurrencyType from;
    private CurrencyType to;

    public OrderExchangeRequest(
        final Long memberId,
        final Long orderId,
        final CurrencyType from,
        final CurrencyType to
    ) {
        this.memberId = memberId;
        this.orderId = orderId;
        this.from = from;
        this.to = to;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public CurrencyType getFrom() {
        return this.from;
    }

    public CurrencyType getTo() {
        return this.to;
    }
}
