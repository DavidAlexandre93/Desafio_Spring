package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.ClienteDTO;
import br.com.meli.desafiospring.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClienteController {

        @Autowired
        private ClienteService clienteService;

    /**
     * Author: David Alexandre
     * Method: End Point
     * Description: listar todos os clientes
     * @return
     */
    @GetMapping("/api/v1/articles/client")
        public ResponseEntity<List<ClienteDTO>> listarClientes() {
            List<ClienteDTO> result = ClienteDTO.converte(clienteService.listClient());
            return ResponseEntity.ok(result);
        }
}
