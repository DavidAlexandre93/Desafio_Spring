package br.com.meli.desafiospring.controller;


import br.com.meli.desafiospring.entity.Product;
import br.com.meli.desafiospring.entity.ProdutoSimplificado;
import br.com.meli.desafiospring.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;



//    static {
//        produtos.addAll(Arrays.asList(
//                new ProdutoSimplificado("Bola", 400.0),
//                new ProdutoSimplificado("Carro", 200.0),
//                new ProdutoSimplificado("Pedra", 500.0),
//                new ProdutoSimplificado("Papel", 700.0),
//                new ProdutoSimplificado("Tesoura", 100.0)
//        ));
//    }


	@GetMapping("/produtos")
    public List<Product> retorna(){

        return ProductService.produtos;
    }

	@PostMapping("/produtos")
    public String salvar(@RequestBody Product product){


        productService.produtos.add(product);


        return "Produto salvo " + product.getName() + " com preço de " + product.getPrice();
    }



    /**
     *  R005, R006, R007
     */
    @GetMapping("/api/v1/articles")
    public List<Product> retornaPorPreco(@RequestParam String category,
                                         @RequestParam Boolean freeShipping,
                                         @RequestParam Integer order){


        ProductService.p.apply(order);

        return productService.findByCritirion(category, freeShipping, order);

    }







}
