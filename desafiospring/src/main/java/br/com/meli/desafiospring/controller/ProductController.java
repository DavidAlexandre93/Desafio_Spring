package br.com.meli.desafiospring.controller;

import br.com.meli.desafiospring.dto.ArticlesDTO;
import br.com.meli.desafiospring.dto.ProductPurchaseRequestDTO;
import br.com.meli.desafiospring.dto.PurchaseRequestDTO;
import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.entity.ShoppingCart;
import br.com.meli.desafiospring.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class do controlador da aplicação REST;
 *
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;


    /**
     * Responsavel pelo operação GET que retorna uma lista com os produtos cadastrados.
     * É possivel adicionar filtros
     *
     * @param category   atributo de Product usada no processo de filtragem;
     * @param freeShipping   atributo de Product usada no processo de filtragem;
     * @param order          opção escolhida para filtragem em relação a ordem alfabetica ou por preço:
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
     * Responsavel pela operação POST que cadastra novos produtos na aplicação;
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
     * Responsavel pela operação POST que faz uma requisição de compra na aplicação;
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
     * Responsavel pelo operação GET que retorna uma lista com os produtos cadastrados, filtrados por categoria.
     *
     * @param category Categoria de produto usada no processo de filtragem;
     *
     * @return Lista de objetos do tipo Product de produtos cadastrados filtrados por categoria;
     */
    @GetMapping("/articles/category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category) {
        List<Product> categories = productService.getProductsByCategory(category);

        return ResponseEntity.ok(categories);
    }

    /**
     * Responsavel pelo operação GET que retorna uma lista com todos os produtos cadastrados.
     *
     * @return Lista com todos os produtos cadastrados
     */
    @GetMapping("/articles/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

}


