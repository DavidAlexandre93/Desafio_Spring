package br.com.meli.desafiospring.exception;

public class OutOfStockException extends RuntimeException {
    private static final long serialVersionUID = -2798746464564465L;

    public OutOfStockException(String message) {
        super(message);
    }
}
