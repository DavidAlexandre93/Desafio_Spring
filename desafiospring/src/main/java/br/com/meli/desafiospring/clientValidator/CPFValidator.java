package br.com.meli.desafiospring.clientValidator;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.exception.ValidacaoException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CPFValidator implements Validator{

    private Client client;

    @Override
    public void valida() throws ValidacaoException {
        if(client.getCpf().length() == 10)
            throw new ValidacaoException("CPF with invalid characters");
    }
}
