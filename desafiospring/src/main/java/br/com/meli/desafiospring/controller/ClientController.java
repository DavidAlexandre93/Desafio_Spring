package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/api/v1/articles/client/state") // metodo para filtrar por categoria
    public ResponseEntity<List<Client>> getClientsByState(@RequestParam String state){
        List<Client> clientByState = clientService.getClientsByState(state);

        return ResponseEntity.ok(clientByState);
    }
}
