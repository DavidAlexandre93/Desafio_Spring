package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.clientValidator.CPFValidator;
import br.com.meli.desafiospring.clientValidator.ClientNameValidator;
import br.com.meli.desafiospring.clientValidator.ClientStateValidator;
import br.com.meli.desafiospring.entity.Client;
import br.com.meli.desafiospring.exception.ClientRegisteredException;
import br.com.meli.desafiospring.exception.ValidacaoException;
import br.com.meli.desafiospring.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2")
public class ClientController {

    ClientService clientService;

    @PostMapping("/insert-client")
    public ResponseEntity<?> postClient(@RequestBody Client input) {
        try {
            new ClientNameValidator(input).valida();
            new CPFValidator(input).valida();
            new ClientStateValidator(input).valida();
            clientService.createClient(input);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (ClientRegisteredException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ValidacaoException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }
}
