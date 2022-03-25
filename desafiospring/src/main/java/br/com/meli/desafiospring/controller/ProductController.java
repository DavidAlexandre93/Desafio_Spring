package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.InputDTO;
import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@AllArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    /**
     * R005, R006, R007
     */
    @GetMapping("/api/v1/articles")
    public List<Product> retornaPorPreco(@RequestParam String category,
                                         @RequestParam Boolean freeShipping,
                                         @RequestParam Integer order) {


        ProductService.p.apply(order);

        return productService.findByCritirion(category, freeShipping, order);

    }

    @PostMapping("/api/v1/insert-articles-request")
    public ResponseEntity<List<ProductDTO>> postProducts(@RequestBody InputDTO input) {
        try {
            return ResponseEntity.ok(productService.createProducts(input));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/v1/articles/category")
    public ResponseEntity <List<Product>> getProductsByCategory(@RequestParam String category){
        List<Product> categories = productService.getProductsByCategory(category);

        return ResponseEntity.ok(categories);
    }



}


