package br.com.meli.desafiospring.exception;

public class ValidacaoException extends Exception {
    private static final long serialVersionUID = 8514406528027778757L;

    public ValidacaoException(String message) {
        super(message);
    }
}
