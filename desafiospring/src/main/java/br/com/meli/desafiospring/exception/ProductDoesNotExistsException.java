package br.com.meli.desafiospring.exception;

import java.io.Serializable;

public class ProductDoesNotExistsException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -43123135543453553L;

    public ProductDoesNotExistsException(String message) {
        super(message);
    }
}
