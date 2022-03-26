package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.ArticlesDTO;
import br.com.meli.desafiospring.dto.ProductDTO;
import br.com.meli.desafiospring.dto.ProductPurchaseRequestDTO;
import br.com.meli.desafiospring.dto.PurchaseRequestDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.entity.ShoppingCart;
import br.com.meli.desafiospring.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    /**
     * R005, R006, R007
     */

    @GetMapping("/articles")
    public List<Product> retornaPorPreco(@RequestParam(required = false) String category,
                                         @RequestParam(required = false) Boolean freeShipping,
                                         @RequestParam(required = false) Integer order) {
        if (category == null && freeShipping == null && order == null) {
            return productService.findAll();
        } else {
            return productService.findByCriteria(category, freeShipping, order);
        }
    }

    /**
     * Author: Bruno Mendes
     * Method: End point post new Product
     * Description: Cria novos produtos recebendo uma lista de produtos
     */
    @PostMapping("/insert-articles-request")
    public ResponseEntity<?> postProducts(@Valid @RequestBody ArticlesDTO input) {
        List<Product> products = productService.createProducts(input);
        List<ProductPurchaseRequestDTO> resultDTO = products
                .stream().map(p -> modelMapper.map(p, ProductPurchaseRequestDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(resultDTO);
    }

    @PostMapping("/purchase-request")
    public ResponseEntity<?> purchaseProducts(@Valid @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        ShoppingCart shoppingCart = modelMapper.map(purchaseRequestDTO, ShoppingCart.class);
        ShoppingCart result = productService.sellProducts(shoppingCart);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/articles/category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category) {
        List<Product> categories = productService.getProductsByCategory(category);

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/articles/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    /**
     * Author: David Alexandre
     * Method: End Point Request Param
     * Description: Buscar os category com seus respectivos freeshipping, sendo true ou false
     *
     * @param category
     * @param freeShipping
     * @return
     */
    @GetMapping("/articles/")
    public ResponseEntity<List<ProductDTO>> categoryFreeshippingRequest(@RequestParam String category, @RequestParam Boolean freeShipping) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorCategoriaFreeshipping(category, freeShipping));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com sua category com seus respectivos freeshipping, sendo true ou false
     *
     * @param category
     * @param freeShipping
     * @return
     */
    @GetMapping("/articles/categoryfreeshipping/{category}/{freeShipping}")
    public ResponseEntity<List<ProductDTO>> categoryFreeshipping(@PathVariable String category, @PathVariable Boolean freeShipping) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorCategoriaFreeshipping(category, freeShipping));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com seu name e respectivo brand
     *
     * @param name
     * @param brand
     * @return
     */
    @GetMapping("/articles/namebrand/{name}/{brand}")
    public ResponseEntity<List<ProductDTO>> nameBrand(@PathVariable String name, @PathVariable String brand) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorNameBrand(name, brand));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com seu price e quantity
     *
     * @param price
     * @param quantity
     * @return
     */
    @GetMapping("/articles/pricequantity/{price}/{quantity}")
    public ResponseEntity<List<ProductDTO>> priceQuantity(@PathVariable BigDecimal price, @PathVariable Integer quantity) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorPriceQuantity(price,quantity));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com seu name e sua respectiva category
     *
     * @param name
     * @param category
     * @return
     */
    @GetMapping("/articles/namecategory/{name}/{category}")
    public ResponseEntity<List<ProductDTO>> nameCategory(@PathVariable String name, @PathVariable String category) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorNameCategory(name, category));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com seu ID e respectivo prestige
     *
     * @param productId
     * @param prestige
     * @return
     */
    @GetMapping("/articles/productprestige/{productId}/{prestige}")
    public ResponseEntity<List<ProductDTO>> productIdPrestige(@PathVariable Long productId, @PathVariable String prestige) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorProductIdPrestige(productId, prestige));
        return ResponseEntity.ok(result);

    }
}
