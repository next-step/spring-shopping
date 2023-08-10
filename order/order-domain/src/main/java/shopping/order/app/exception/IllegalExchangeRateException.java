package shopping.order.app.exception;

import java.text.MessageFormat;

public class IllegalExchangeRateException extends RuntimeException {

    public IllegalExchangeRateException(double rate) {
        super(MessageFormat.format("exchange rate \"{0}\" 가 0 이하입니다.", rate));
    }

}
