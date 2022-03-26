package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.exception.ClientRegisteredException;
import br.com.meli.desafiospring.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService {


    ClientRepository clientRepository;

    public void createClient(Client input) {
        List<Client> existingClients = clientRepository.findAll();
        AtomicReference<Boolean> exist = new AtomicReference<>(false);
        if (existingClients.isEmpty()) {
            clientRepository.writeFile(input);
        } else {
            existingClients.stream().forEach(c -> {
                if (c.getId().equals(input.getId()) || c.getCpf().equals(input.getCpf())) {
                    exist.set(true);
                }
            });
            if (!exist.get()) {
                clientRepository.writeFile(input);
            } else {
                throw new ClientRegisteredException("Client already registered");
            }
        }
    }
    /**
     * Author: Mariana Galdino
     * Method: retorna lista de clientes filtrados
     * Description: leitura da list de clientes e retorna filtrados pelo estado
     * @return
     */
    public List<Client> getClientsByState(String state){

        List<Client> clientByState = clientRepository.findAll();

        if(state!=null && !state.isEmpty() ) {
            return clientByState.stream()
                    .filter(client -> client.getState().equalsIgnoreCase(state))
                    .collect(Collectors.toList());
        }
        return clientByState;

    }

    /**
     * Author: David Alexandre
     * Method: Retornar a lista de cliente
     * Description: Realizar a leitura da lista e retornar todos os clientes
     * @return
     */
    public List<Client> listClient(){
        List<Client> clienteList = new ArrayList<>();
        for (Client c: clientRepository.findAll()){

            clienteList.add(c);
        }
        return clienteList;
    }
}
