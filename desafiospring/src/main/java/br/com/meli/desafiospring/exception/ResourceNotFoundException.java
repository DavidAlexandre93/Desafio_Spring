package br.com.meli.desafiospring.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4870885287350391097L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
