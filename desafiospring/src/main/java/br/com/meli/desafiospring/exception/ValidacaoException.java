package br.com.meli.desafiospring.exception;

/**
 * Author: Bruno Mendes
 * Method: Excepstion personalizada para validações
 * Description: cria uma exception personalizada para as validações
 */
public class ValidacaoException extends Exception {
    private static final long serialVersionUID = 8514406528027778757L;

    public ValidacaoException(String message) {
        super(message);
    }
}
