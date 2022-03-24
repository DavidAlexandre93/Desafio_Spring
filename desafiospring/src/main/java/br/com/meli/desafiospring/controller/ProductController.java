package br.com.meli.desafiospring.controller;


import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.repository.ProductRepository;
import br.com.meli.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
/*@Api(value = "Produtos")*/
public class ProductController {

    @Autowired
    //private ProductRepository<ProductService> productService;
    private ProductService productService;

    /*@ApiOperation(value = "Fetch all existing ads based on params")*/
    @GetMapping("/api/v1/articles/{category}/{freeShipping}")
    public ResponseEntity<List<ProductDTO>> retorna(@PathVariable String category, @PathVariable String freeShipping) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorCategoriaFreeshipping(category,freeShipping));
        return ResponseEntity.ok(result);
    }

    /*@ApiOperation(value = "Fetch all existing ads based on params")
    @GetMapping("/api/v1/articles/")
    public ResponseEntity<List<ProductDTO>> obter(String category) {
        Product product = productService.obter(category);
        return ResponseEntity.ok(new ProductDTO().converte((List<Product>) product));
    }*/
}

