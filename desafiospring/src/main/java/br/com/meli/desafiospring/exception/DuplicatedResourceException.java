package br.com.meli.desafiospring.exception;

public class DuplicatedResourceException extends RuntimeException {
    private static final long serialVersionUID = -2820885287350341071L;

    public DuplicatedResourceException(String message) {
        super(message);
    }
}
