package br.com.meli.desafiospring.service;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private Client client;

    @Autowired
    private ClienteRepository<Client> repository;

    public List<Client> listaClientes(String name){
        List<Client> clienteList = new ArrayList<>();
        for (Client c: repository.lista()){
            if (c.getName().equals(name)){
                clienteList.add(c);
            }
        }
        return clienteList;
    }
}
