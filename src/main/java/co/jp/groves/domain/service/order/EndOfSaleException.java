package co.jp.groves.domain.service.order;

public class EndOfSaleException extends RuntimeException {
    public EndOfSaleException(String message) {
        super(message);
    }
}
