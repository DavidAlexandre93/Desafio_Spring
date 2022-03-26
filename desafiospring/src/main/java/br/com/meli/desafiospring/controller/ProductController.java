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
     * Author:
     * Method: End Point Request Param
     * Description: Responsavel pelo operação GET que retorna uma lista com os produtos cadastrados;
     * É possivel realizar processos de filtragem na lista de produtos em função dos parametros passados;
     *
     * @param category       Atributo da Class Product usada no processo de filtragem;
     * @param freeShipping   Atributo da Class Product usada no processo de filtragem;
     * @param order          Opção escolhida para filtragem em relação a ordem alfabetica ou por preço:
     *                       order = 0: Orderm alfabética crescente;
     *                       order = 1: Orderm alfabética decrescente;
     *                       order = 2: Ordem do maior preço para o menor;
     *                       order = 3: Ordem do menor preço para o maior;
     *
     * @return  Lista de objetos do tipo Product de produtos cadastrados filtrados ou não;
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
     * Description: Responsavel pela operação POST que cadastra novos produtos na aplicação ao receber uma lista de produtos;
     *
     * @param input Entrada com lista de objetos seguindo os atribuitos da Class Product no formato JSON;
     *
     * @return Mensagem de confirmação ou não de cadastro e informações no formato DTO dos produtos cadastrados;
     */

    @PostMapping("/insert-articles-request")
    public ResponseEntity<?> postProducts(@Valid @RequestBody ArticlesDTO input) {
        List<Product> products = productService.createProducts(input);
        List<ProductPurchaseRequestDTO> resultDTO = products
                .stream().map(p -> modelMapper.map(p, ProductPurchaseRequestDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(resultDTO);
    }

    /**
     * Author:
     * Method:
     * Description: Responsavel pela operação POST que faz uma requisição de compra na aplicação;
     *
     * @param purchaseRequestDTO Lista de objetos do tipo Product correspondentes a lista de compra;
     *
     * @return Mensagem de confirmação ou não da requisição de compra e informações sobre os produtos informados
     *          na requisição de compra mais valor total da compra;
     */
    @PostMapping("/purchase-request")
    public ResponseEntity<?> purchaseProducts(@Valid @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        ShoppingCart shoppingCart = modelMapper.map(purchaseRequestDTO, ShoppingCart.class);
        ShoppingCart result = productService.sellProducts(shoppingCart);
        return ResponseEntity.ok(result);
    }

    /**
     * Author: Mariana Galdino
     * Method: End Point
     * Description: listar todos os clientes filtrados por categoria
     * @return
     */
    @GetMapping("/articles/category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category) {
        List<Product> categories = productService.getProductsByCategory(category);

        return ResponseEntity.ok(categories);
    }

    /**
     * Author:
     * Method:
     * Description: Responsavel pelo operação GET que retorna uma lista com todos os produtos cadastrados.
     *
     * @return Lista com todos os produtos cadastrados
     */
    @GetMapping("/articles/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    /**
     * Author: David Alexandre
     * Method: End Point Request Param
     * Description: Buscar os category com seus respectivos freeshipping, sendo true ou false
     *
     * @param category Atributo da Class Product usada no processo de filtragem;
     * @param freeShipping Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por categoria;
     */
    @GetMapping("/articles/listcategoryfreeshipping")
    public ResponseEntity<List<ProductDTO>> categoryFreeshippingRequest(@RequestParam String category,
                                                                        @RequestParam Boolean freeShipping) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorCategoriaFreeshipping(category,freeShipping));
        return ResponseEntity.ok(result);
    }

    /**
     * Author: David Alexandre
     * Method: End Point Path Variable
     * Description: Buscar o produto com sua category com seus respectivos freeshipping, sendo true ou false
     *
     * @param category Atributo da Class Product usada no processo de filtragem;
     * @param freeShipping Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por categoria e possibilidade de envio gratis;
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
     * @param name Atributo da Class Product usada no processo de filtragem;
     * @param brand Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por nome e marca;
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
     * @param price Atributo da Class Product usada no processo de filtragem;
     * @param quantity Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por preco e quantidade disponivel;
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
     * @param name Atributo da Class Product usada no processo de filtragem;
     * @param category Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por nome e categoria;
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
     * @param productId Atributo da Class Product usada no processo de filtragem;
     * @param prestige Atributo da Class Product usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por Id do produto e prestigio;
     */
    @GetMapping("/articles/productprestige/{productId}/{prestige}")
    public ResponseEntity<List<ProductDTO>> productIdPrestige(@PathVariable Long productId, @PathVariable String prestige) {
        List<ProductDTO> result = ProductDTO.converte(productService.listaPorProductIdPrestige(productId, prestige));
        return ResponseEntity.ok(result);

    }
}
