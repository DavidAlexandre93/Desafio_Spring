package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
/*@Api(value = "Produtos")*/
public class ProductController {

    @Autowired
    //private ProductRepository<ProductService> productService;
    private ProductService productService;

    /*@ApiOperation(value = "Fetch all existing ads based on params")*/
    @GetMapping("/api/v1/articles/")
    public ResponseEntity<List<ProductDTO>> categoryFreeshippingRequest(@RequestParam String category, @RequestParam String freeShipping) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorCategoriaFreeshipping(category,freeShipping));
        return ResponseEntity.ok(result);
    }

    /*@ApiOperation(value = "Fetch all existing ads based on params")*/
    @GetMapping("/api/v1/articles/{category}/{freeShipping}")
    public ResponseEntity<List<ProductDTO>> categoryFreeshipping(@PathVariable String category, @PathVariable String freeShipping) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorCategoriaFreeshipping(category,freeShipping));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/v2/articles/{name}/{brand}")
    public ResponseEntity<List<ProductDTO>> nameBrand(@PathVariable String name, @PathVariable String brand){
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorNameBrand(name, brand));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/v3/articles/{price}/{quantity}")
    public ResponseEntity<List<ProductDTO>> priceQuantity(@PathVariable String price, @PathVariable String quantity){
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorPriceQuantity(price, quantity));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/v4/articles/{name}/{category}")
    public ResponseEntity<List<ProductDTO>> nameCategory(@PathVariable String name, @PathVariable String category){
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorNameCategory(name, category));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/v5/articles/{productId}/{prestige}")
    public ResponseEntity<List<ProductDTO>> productIdPrestige(@PathVariable String productId, @PathVariable String prestige){
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorProductIdPrestige(productId, prestige));
        return ResponseEntity.ok(result);
    }

    /*@ApiOperation(value = "Fetch all existing ads based on params")
    @GetMapping("/api/v1/articles/")
    public ResponseEntity<List<ProductDTO>> obter(String category) {
        Product product = productService.obter(category);
        return ResponseEntity.ok(new ProductDTO().converte((List<Product>) product));
    }*/
}

