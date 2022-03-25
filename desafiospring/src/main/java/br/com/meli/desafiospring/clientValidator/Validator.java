package br.com.meli.desafiospring.clientValidator;

import br.com.meli.desafiospring.exception.ValidacaoException;

public interface Validator {

    void valida() throws ValidacaoException;
}
