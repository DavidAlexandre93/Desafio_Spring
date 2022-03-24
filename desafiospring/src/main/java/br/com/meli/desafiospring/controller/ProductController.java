package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/api/v1/articles")
    public ResponseEntity <List<Product>> getProductsByCategory(@RequestParam String category){
        List<Product> categories = productService.getProductsByCategory(category);

        return ResponseEntity.ok(categories);
    }
}
