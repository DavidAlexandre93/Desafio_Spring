package br.com.meli.desafiospring.exception;

/**
 * Author: Bruno Mendes
 * Method: Exception para um cliente existente
 * Description: cria uma exception para clientes ja caastrados
 */

public class ClientRegisteredException extends RuntimeException{
    private static final long serialVersionUID = -4870885287350391078L;

    public ClientRegisteredException(String message) {
        super(message);
    }
}
