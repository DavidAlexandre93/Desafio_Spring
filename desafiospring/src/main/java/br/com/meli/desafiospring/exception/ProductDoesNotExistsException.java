package br.com.meli.desafiospring.exception;

public class ProductDoesNotExistsException extends RuntimeException {
    private static final long serialVersionUID = -4870885287350391097L;

    public ProductDoesNotExistsException(String message) {
        super(message);
    }
}
