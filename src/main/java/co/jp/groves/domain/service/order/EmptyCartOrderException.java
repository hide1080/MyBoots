package co.jp.groves.domain.service.order;

public class EmptyCartOrderException extends RuntimeException {
    public EmptyCartOrderException(String message) {
        super(message);
    }
}
