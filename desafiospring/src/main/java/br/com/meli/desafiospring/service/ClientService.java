package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> getClientsByState(String state){

        List<Client> clientByState = clientRepository.clients();

        if(state!=null && !state.isEmpty() ) {
            return clientByState.stream()
                    .filter(client -> client.getState().equalsIgnoreCase(state))
                    .collect(Collectors.toList());
        }
        return clientByState;
    }
}
