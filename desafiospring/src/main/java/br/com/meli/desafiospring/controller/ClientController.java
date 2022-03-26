package br.com.meli.desafiospring.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
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

    private final ClientService clientService;

    @PostMapping("/insert-client")
    public ResponseEntity<?> postClient(@Valid @RequestBody ClientInputValidationDTO input) {
        System.out.println(input);
        try {
            Client client = modelMapper.map(input, Client.class);
            clientService.createClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (ClientRegisteredException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    /**
     * Author: Mariana Galdino
     * Method: End Point
     * Description: listar todos os clientes filtrados por estado
     * @return
     */
    @GetMapping("/articles/client/state") // metodo para filtrar por estado
    public ResponseEntity<List<Client>> getClientsByState(@RequestParam String state) {

        List<Client> clientByState = clientService.getClientsByState(state);

        return ResponseEntity.ok(clientByState);
    }
    /**
     * Author: David Alexandre
     * Method: End Point
     * Description: listar todos os clientes
     * @return
     */
    @GetMapping("/articles/list-client")
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> result = ClienteDTO.converte(clientService.listClient());
        return ResponseEntity.ok(result);
    }
}
