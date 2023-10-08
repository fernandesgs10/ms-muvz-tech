package br.com.gateway.buy.product.common;

public class MuvzException extends RuntimeException {

    public MuvzException(String message) {
        super(message);
    }

    public MuvzException(String message, Throwable cause) {
        super(message, cause);
    }
}
