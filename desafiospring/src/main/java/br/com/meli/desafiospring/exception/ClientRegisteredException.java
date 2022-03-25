package br.com.meli.desafiospring.exception;

public class ClientRegisteredException extends RuntimeException{
    private static final long serialVersionUID = -4870885287350391078L;

    public ClientRegisteredException(String message) {
        super(message);
    }
}
