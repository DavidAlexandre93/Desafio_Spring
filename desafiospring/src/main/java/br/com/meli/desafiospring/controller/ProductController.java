package br.com.meli.desafiospring.controller;


import br.com.meli.desafiospring.entity.ProdutoSimplificado;
import br.com.meli.desafiospring.service.ProductService;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ProductController {


    	private static List<ProdutoSimplificado> produtos = Collections.synchronizedList(new ArrayList<ProdutoSimplificado>());

    static {
		produtos.addAll(Arrays.asList(
				new ProdutoSimplificado("Xadrez", BigDecimal.valueOf(400)),
                new ProdutoSimplificado("Carro", BigDecimal.valueOf(200)),
                new ProdutoSimplificado("Pedra", BigDecimal.valueOf(500)),
                new ProdutoSimplificado("Lapis", BigDecimal.valueOf(700)),
                new ProdutoSimplificado("Tesoura", BigDecimal.valueOf(100))
			));
	}

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
    public List<ProdutoSimplificado> retorna(){

        return produtos;
    }

	@PostMapping("/produtos")
    public String salvar(@RequestBody ProdutoSimplificado produtoSimplificado){


        produtos.add(produtoSimplificado);


        return "Produto salvo " + produtoSimplificado.getName() + " com preço de " + produtoSimplificado.getPrice();
    }



    // Desafio
    @GetMapping("/api/v1/articles")
    public List<ProdutoSimplificado> retornaPorPreco(@RequestParam String category,
                                                       @RequestParam String freeShipping,
                                                       @RequestParam Integer order){

        List<ProdutoSimplificado> list;
        list = produtos;
        // Adicionar tratamento para null

        ProductService.p.apply(order);

        return list.stream().sorted(ProductService.p.apply(order)).collect(Collectors.toList());


//        if(order == 2){
//            list = produtos
//                    .stream()
//                    .sorted(Comparator.comparingDouble(v -> v.getPrice().doubleValue()))
//                    .collect(Collectors.toList());
//
//            return   list;
//
//        }
//        else if(order == 3){
//            list = produtos
//                    .stream()
//                    .sorted(Comparator.comparingDouble(v -> v.getPrice().doubleValue()))
//                    .collect(Collectors.toList());
//            Collections.reverse(list);
//
//            return   list;
//
//        }
//        else
//            return produtos;



    }





//    @GetMapping("/api/v1/articles")
//    public List<ProdutoSimplificado> retornaMaiorPreco(@RequestParam String category,
//                                                     @RequestParam String freeShipping,
//                                                     @RequestParam String order){
//
//        List<ProdutoSimplificado> list;
//
//        list = produtos
//                .stream()
//                .sorted(Comparator.comparingDouble(v -> v.getPrice().doubleValue()))
//                .collect(Collectors.toList());
//
//        Collections.reverse(list);
//        return   list;
//
//
//    }





}
