package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.InputDTO;
import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @PostMapping("/api/v1/insert-articles-request")
    public ResponseEntity<List<ProductDTO>> postProducts(@RequestBody InputDTO input) {
        System.out.println(input.getArticles());
        return ResponseEntity.ok(input.getArticles().stream().map(a -> new ProductDTO().convert(a)).collect(Collectors.toList()));
    }
}
