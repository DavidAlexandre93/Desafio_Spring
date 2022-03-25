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
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Author: David Alexandre
     * Method: End Point Request Param
     * Description: Buscar os category com seus respectivos freeshipping, sendo true ou false
     * @param category
     * @param freeShipping
     * @return
     */
    @GetMapping("/api/v1/articles/")
    public ResponseEntity<List<ProductDTO>> categoryFreeshippingRequest(@RequestParam String category, @RequestParam String freeShipping) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorCategoriaFreeshipping(category,freeShipping));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com sua category com seus respectivos freeshipping, sendo true ou false
     * @param category
     * @param freeShipping
     * @return
     */
    @GetMapping("/api/v1/articles/categoryfreeshipping/{category}/{freeShipping}")
    public ResponseEntity<List<ProductDTO>> categoryFreeshipping(@PathVariable String category, @PathVariable String freeShipping) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorCategoriaFreeshipping(category,freeShipping));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com seu name e respectivo brand
     * @param name
     * @param brand
     * @return
     */
    @GetMapping("/api/v1/articles/namebrand/{name}/{brand}")
    public ResponseEntity<List<ProductDTO>> nameBrand(@PathVariable String name, @PathVariable String brand){
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorNameBrand(name, brand));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com seu price e quantity
     * @param price
     * @param quantity
     * @return
     */
    @GetMapping("/api/v1/articles/pricequantity/{price}/{quantity}")
    public ResponseEntity<List<ProductDTO>> priceQuantity(@PathVariable String price, @PathVariable String quantity){
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorPriceQuantity(price, quantity));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com seu name e sua respectiva category
     * @param name
     * @param category
     * @return
     */
    @GetMapping("/api/v1/articles/namecategory/{name}/{category}")
    public ResponseEntity<List<ProductDTO>> nameCategory(@PathVariable String name, @PathVariable String category){
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorNameCategory(name, category));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com seu ID e respectivo prestige
     * @param productId
     * @param prestige
     * @return
     */
    @GetMapping("/api/v1/articles/productprestige/{productId}/{prestige}")
    public ResponseEntity<List<ProductDTO>> productIdPrestige(@PathVariable String productId, @PathVariable String prestige){
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorProductIdPrestige(productId, prestige));
        return ResponseEntity.ok(result);
    }

}

