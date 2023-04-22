package co.jp.groves.domain.service.order;

public class InvalidCartOrderException extends RuntimeException {
    public InvalidCartOrderException(String message) {
        super(message);
    }
}
