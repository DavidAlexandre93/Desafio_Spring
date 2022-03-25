package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.InputDTO;
import br.com.meli.desafiospring.dto.ProductPurchaseRequestDTO;
import br.com.meli.desafiospring.dto.PurchaseRequestDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.entity.ShoppingCart;
import br.com.meli.desafiospring.service.ProductService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private final ProductService productService;
    private final ModelMapper modelMapper;

    /**
     * R005, R006, R007
     */

    @GetMapping("/articles")
    public List<Product> retornaPorPreco(@RequestParam(required=false) String category,
                                         @RequestParam(required=false) Boolean freeShipping,
                                         @RequestParam(required=false) Integer order) {
        if (category == null && freeShipping == null && order == null) {
            return productService.findAll();
        } else {
            ProductService.p.apply(order);
            return productService.findByCriteria(category, freeShipping, order);
        }

    }

    @PostMapping("/insert-articles-request")
    public ResponseEntity<?> postProducts(@RequestBody InputDTO input) {
        try {
            return ResponseEntity.ok(productService.createProducts(input));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/purchase-request")
    public ResponseEntity<?> purchaseProducts(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        ShoppingCart shoppingCart = modelMapper.map(purchaseRequestDTO, ShoppingCart.class);
        List<Product> soldProducts = productService.sellProducts(shoppingCart);
        return ResponseEntity.ok(soldProducts);
    }
    @GetMapping("/articles/category")
    public ResponseEntity <List<Product>> getProductsByCategory(@RequestParam String category){
        List<Product> categories = productService.getProductsByCategory(category);

        return ResponseEntity.ok(categories);
    }

}


