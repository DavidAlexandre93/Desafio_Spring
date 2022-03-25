package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.ClientInputValidationDTO;
import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.exception.ClientRegisteredException;
import br.com.meli.desafiospring.service.ClientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2")
public class ClientController {

    private final ModelMapper modelMapper;
    ClientService clientService;

    @PostMapping("/insert-client")
    public ResponseEntity<?> postClient(@Valid @RequestBody ClientInputValidationDTO input) {
        try {
            Client client = modelMapper.map(input, Client.class);
            clientService.createClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (ClientRegisteredException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
