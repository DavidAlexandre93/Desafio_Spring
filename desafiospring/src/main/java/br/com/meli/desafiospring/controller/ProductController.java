package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.InputDTO;
import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ProductController {

    private ProductService productService = new ProductService();

    @PostMapping("/api/v1/insert-articles-request")
    public ResponseEntity<List<ProductDTO>> postProducts(@RequestBody InputDTO input) {
        try {
            return ResponseEntity.ok(productService.createProducts(input));

        } catch ( Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
