package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.InputDTO;
import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ProductController {

    //ResponseEntity<List<ProductDTO>>
    @PostMapping("/api/v1/insert-articles-request")
    public ResponseEntity<Object> postProducts(@RequestBody InputDTO input) {
        System.out.println(input.getArticles());
        return ResponseEntity.ok(input);
    }
}
