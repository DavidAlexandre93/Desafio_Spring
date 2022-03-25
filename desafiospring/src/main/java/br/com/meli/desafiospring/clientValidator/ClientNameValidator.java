package br.com.meli.desafiospring.clientValidator;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.exception.ValidacaoException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientNameValidator implements Validator {

    private Client client;

    @Override
    public void valida() throws ValidacaoException {
        if(client.getName().length() < 3)
            throw new ValidacaoException("Minimum name size is 3");
    }
}
