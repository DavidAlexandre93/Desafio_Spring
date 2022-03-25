package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.ClientInputValidationDTO;
import br.com.meli.desafiospring.dto.ClienteDTO;
import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.exception.ClientRegisteredException;
import br.com.meli.desafiospring.service.ClientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    /**
     * Author: David Alexandre
     * Method: End Point
     * Description: listar todos os clientes
     * @return
     */
    @GetMapping("/api/v1/articles/client")
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> result = ClienteDTO.converte(clientService.listClient());
        return ResponseEntity.ok(result);
    }
}
