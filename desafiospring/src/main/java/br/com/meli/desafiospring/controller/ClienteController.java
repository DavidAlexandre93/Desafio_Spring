package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.ClienteDTO;
import br.com.meli.desafiospring.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClienteController {

        @Autowired
        //private ProductRepository<ProductService> productService;
        private ClienteService clienteService;

        /*@ApiOperation(value = "Fetch all existing ads based on params")*/
        @GetMapping("/api/v13/articles/{name}")
        public ResponseEntity<List<ClienteDTO>> listarClientes(@PathVariable String name) {
            List<ClienteDTO> result = ClienteDTO.converte(clienteService.listaClientes(name));
            return ResponseEntity.ok(result);
        }
}
