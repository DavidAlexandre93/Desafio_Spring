package br.com.meli.desafiospring.clientValidator;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.exception.ValidacaoException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientStateValidator implements Validator{

    private Client client;

    @Override
    public void valida() throws ValidacaoException {
        if(client.getState().length() < 4)
            throw new ValidacaoException("Minimum state size is 4");
    }
}
